package edu.ucdavis.ucdh.stu.core.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javassist.NotFoundException;

import edu.ucdavis.ucdh.stu.core.beans.MenuItem;
import edu.ucdavis.ucdh.stu.core.manager.MenuItemManager;
import edu.ucdavis.ucdh.stu.core.utils.DefaultMenuItemFilter;
import edu.ucdavis.ucdh.stu.core.utils.MenuItemFilter;

@Path("/menu/{context}")
@Component
@Scope("request")
public class MenuItemResource {
	private Log log = LogFactory.getLog(getClass());
	private MenuItemManager menuItemManager;
	private MenuItemFilter menuItemFilter = new DefaultMenuItemFilter();

	@GET
	@Path("/{name}")
	@Produces("text/xml")
	public MenuItem getMenuItem(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		MenuItem menuItem = menuItemManager.findByContextAndName(context, name);
		if (menuItem == null) {
			throw new NotFoundException("No such menu.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning menuItem " + menuItem.getId());
		}
		return menuItemFilter.getAuthorizedMenuItems(req, menuItem);
	}

	@POST
	@Path("/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public MenuItem saveMenuItem(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("name") String name,
			MultivaluedMap<String, String> formParams) {

		Integer size = getNumericParameter(formParams, "size");
		if (size > 0) {
			String userId = "MenuItemResource";
			if (StringUtils.isNotEmpty(req.getRemoteUser())) {
				userId = req.getRemoteUser();
			}
			Date rightNow = new Date();
			// first, go out and get the main item
			MenuItem mainItem = menuItemManager.findByContextAndName(context, name);
			if (mainItem == null) {
				// if there is not one on file, create it
				mainItem = new MenuItem();
				mainItem.setContext(context);
				mainItem.setName(name);
				mainItem.setCreationDate(rightNow);
				mainItem.setCreatedBy(userId);
				mainItem.setLastUpdate(rightNow);
				mainItem.setLastUpdateBy(userId);
				menuItemManager.save(mainItem);
			}
			List<MenuItem> items = new ArrayList<MenuItem>();
			// next, gather up and build all of the individual items
			for (int i=0; i<size; i++) {
				MenuItem menuItem = new MenuItem();
				Integer id = getNumericParameter(formParams, "id" + i);
				if (id > 0) {
					menuItem = menuItemManager.findById(id);
				} else {
					menuItem.setContext(context);
					menuItem.setName(name);
					menuItem.setCreationDate(rightNow);
					menuItem.setCreatedBy(userId);
				}
				menuItem.setLabel(formParams.getFirst("label" + i));
				menuItem.setDestination(formParams.getFirst("destination" + i));
				menuItem.setAuthorizedRoles(formParams.getFirst("roles" + i));
				menuItem.setLastUpdate(rightNow);
				menuItem.setLastUpdateBy(userId);
				if (id == 0) {
					menuItemManager.save(menuItem);
				}
				items.add(menuItem);
			}
			// then, grab the main menu item and clean it out
			List<MenuItem> origItems = mainItem.getMenuItem();
			if (origItems != null) {
				mainItem.getMenuItem().removeAll(origItems);
			}
			// now, loop through and rebuild the tree ...
			for (int i=0; i<size; i++) {
				MenuItem thisItem = items.get(i);
				origItems = thisItem.getMenuItem();
				if (origItems != null) {
					thisItem.getMenuItem().removeAll(origItems);
				}
				Integer depth = getNumericParameter(formParams, "depth" + i);
				MenuItem parentItem = mainItem;
				if (depth > 0) {
					parentItem = whosYourDaddy(formParams, items, i);
				}
				parentItem.addMenuItem(thisItem);
			}
			// now, save the entire tree
			menuItemManager.save(mainItem);
		}

		return menuItemManager.findByContextAndName(context, name);
	}

	private MenuItem whosYourDaddy(MultivaluedMap<String, String> params, List<MenuItem> items, int index) {
		MenuItem parentItem = null;

		Integer depth = getNumericParameter(params, "depth" + index);
		for (int i=index - 1; i > -1 && parentItem == null; i--) {
			Integer thisDepth = getNumericParameter(params, "depth" + i);
			if (thisDepth < depth) {
				parentItem = items.get(i);
			}
		}

		return parentItem;
	}

	private Integer getNumericParameter(MultivaluedMap<String, String> params, String key) {
		Integer value = new Integer(0);

		String string = params.getFirst(key);
		try {
			if (StringUtils.isNumeric(string)) {
				value = new Integer(string);
			}
		} catch (NumberFormatException e) {
			// no one cares
		}

		return value;
	}

	@DELETE
	@Path("/{name}")
	@Produces("text/xml")
	public String deleteMenuItem(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		MenuItem menuItem = menuItemManager.findByContextAndName(context, name);
		if (menuItem == null) {
			throw new NotFoundException("No such menuItem.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting menuItem " + menuItem.getId());
		}
		menuItemManager.delete(menuItem);
		return "<message>Property deleted.</message>";
	}

	/**
	 * @param menuItemManager the menuItemManager to set
	 */
	public void setMenuItemManager(MenuItemManager menuItemManager) {
		this.menuItemManager = menuItemManager;
	}

	/**
	 * @param menuItemFilter the menuItemFilter to set
	 */
	public void setMenuItemFilter(MenuItemFilter menuItemFilter) {
		this.menuItemFilter = menuItemFilter;
	}
}
