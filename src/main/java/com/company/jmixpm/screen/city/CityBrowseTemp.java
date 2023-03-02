package com.company.jmixpm.screen.city;

import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.City;

@UiController("CityBrowseTemp")
@UiDescriptor("city-browse-temp.xml")
@LookupComponent("citiesTable")
public class CityBrowseTemp extends StandardLookup<City> {
}