package ru.pnzgu.crm.util.mapping;

import lombok.experimental.UtilityClass;
import ru.pnzgu.crm.dto.*;
import ru.pnzgu.crm.store.entity.*;

@UtilityClass
public class Mappers {
    public static final SimpleMapper<ActivityDto, Activity> ACTIVITY = new SimpleMapper<>(new ActivityDto(), new Activity());
    public static final SimpleMapper<ContactDto, Contact> CONTACT = new SimpleMapper<>(new ContactDto(), new Contact());
    public static final SimpleMapper<DealDto, Deal> DEAL = new SimpleMapper<>(new DealDto(), new Deal());
    public static final SimpleMapper<DogovorDto, Dogovor> DOGOVOR = new SimpleMapper<>(new DogovorDto(), new Dogovor());
    public static final SimpleMapper<LeadDto, Lead> LEAD = new SimpleMapper<>(new LeadDto(), new Lead());
    public static final SimpleMapper<ManagerDto, Manager> MANAGER = new SimpleMapper<>(new ManagerDto(), new Manager());
    public static final SimpleMapper<OrderDto, Order> ORDER = new SimpleMapper<>(new OrderDto(), new Order());
    public static final SimpleMapper<ProductDto, Product> PRODUCT = new SimpleMapper<>(new ProductDto(), new Product());
    public static final SimpleMapper<SostavLeadDto, SostavLead> SOSTAV_LEAD = new SimpleMapper<>(new SostavLeadDto(), new SostavLead());
    public static final SimpleMapper<DealSostavDto, DealProduct> DEAL_PRODUCT = new SimpleMapper<>(new DealSostavDto(), new DealProduct());
}
