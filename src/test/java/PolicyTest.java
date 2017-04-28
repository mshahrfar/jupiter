//
// Jupiter: A Ride-Sharing Network Generation and Analysis Application
// Copyright 2017 Mariam Shahrabifarahani <mshahrfar@gmail.com>
// Released under the terms of MIT License
// https://github.com/mshahrfar/jupiter/blob/master/LICENSE
//

package com.mshahrfar.jupiter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.maps.model.LatLng;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author Pejman Ghorbanzade
 */
public class PolicyTest {

    /**
     *
     */
    @Test
    public void calculateDirectDistance() {
      LatLng point1 = new LatLng(40.83954620, -73.94101715);
      LatLng point2 = new LatLng(40.77891159, -73.95391846);
      Distance dist = new Distance(point1, point2);
      assertThat(dist.getShortestPath(), is(closeTo(6829, 1)));
    }

    /**
     *
     */
    @Test
    public void checkVicinityFilter() {
      Customer customerA = new Customer(1);
      Customer customerB = new Customer(2);
      customerA.set("pickup_location", new LatLng(40.83954620, -73.94101715));
      customerB.set("pickup_location", new LatLng(40.77891159, -73.95391846));
      Filter filter = new VicinityFilter(7000);
      assertThat(filter.pass(customerA, customerB), is(true));
      assertThat((new VicinityFilter()).pass(customerA, customerB), is(false));
    }

    /**
     *
     */
    @Test
    public void validCapacity() {
        Filter filter = new RideCapacityFilter(4);
        Customer customerA = new Customer(1);
        Customer customerB = new Customer(2);
        customerA.set("passenger_count", 1);
        customerB.set("passenger_count", 2);
        assertThat(filter.pass(customerA, customerB), is(true));
    }

    /**
     *
     */
    @Test
    public void invalidCapacity() {
        Filter filter = new RideCapacityFilter();
        Customer customerA = new Customer(1);
        Customer customerB = new Customer(2);
        customerA.set("passenger_count", 3);
        customerB.set("passenger_count", 2);
        assertThat(filter.pass(customerA, customerB), is(false));
    }

}
