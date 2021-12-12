package ru.pnzgu.crm.util.mapping;

import lombok.experimental.UtilityClass;
import ru.pnzgu.crm.dto.*;
import ru.pnzgu.crm.store.entity.*;

@UtilityClass
public class Mappers {
    public static final SimpleMapper<ActivityDto, Activity> ACTIVITY_MAPPER = new SimpleMapper<>(new ActivityDto(), new Activity());
    public static final SimpleMapper<ContactDto, Contact> CONTACT_MAPPER = new SimpleMapper<>(new ContactDto(), new Contact());
    public static final SimpleMapper<DealDto, Deal> DEAL_MAPPER = new SimpleMapper<>(new DealDto(), new Deal());
    public static final SimpleMapper<DogovorDto, Dogovor> DOGOVOR_MAPPER = new SimpleMapper<>(new DogovorDto(), new Dogovor());
    public static final SimpleMapper<LeadDto, Lead> LEAD_MAPPER = new SimpleMapper<>(new LeadDto(), new Lead());
    public static final SimpleMapper<ManagerDto, Manager> MANAGER_MAPPER = new SimpleMapper<>(new ManagerDto(), new Manager());
    public static final SimpleMapper<OrderDto, Order> ORDER_MAPPER = new SimpleMapper<>(new OrderDto(), new Order());
    public static final SimpleMapper<ProductDto, Product> PRODUCT_MAPPER = new SimpleMapper<>(new ProductDto(), new Product());
    public static final SimpleMapper<SostavLeadDto, SostavLead> SOSTAV_LEAD_MAPPER = new SimpleMapper<>(new SostavLeadDto(), new SostavLead());
}
