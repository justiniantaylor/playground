package com.pizzeria.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import com.pizzeria.resource.controller.campaign.CampaignController;
import com.pizzeria.resource.controller.customer.CustomerController;
import com.pizzeria.resource.controller.customer.address.AddressController;
import com.pizzeria.resource.controller.menu.MenuController;
import com.pizzeria.resource.controller.menu.item.MenuItemController;
import com.pizzeria.resource.controller.order.OrderController;
import com.pizzeria.resource.controller.order.item.OrderItemController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceApplicationTests {

	@Autowired
    protected CampaignController campaignController;
	
	@Autowired
    protected CustomerController customerController;
	
	@Autowired
    protected AddressController addressController;
	
	@Autowired
    protected MenuController menuController;
	
	@Autowired
    protected MenuItemController menuItemController;
	
	@Autowired
    protected OrderController orderController;
	
	@Autowired
    protected OrderItemController orderItemController;
	
	@Test
	public void contextLoads() {
		assertThat(campaignController).isNotNull();
		assertThat(customerController).isNotNull();
		assertThat(addressController).isNotNull();
		assertThat(menuController).isNotNull();
		assertThat(menuItemController).isNotNull();
		assertThat(orderController).isNotNull();
		assertThat(orderItemController).isNotNull();
	}
}
