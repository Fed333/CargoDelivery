package com.epam.cargo.service;

import com.epam.cargo.algorithms.Dijkstra;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CityUtils {

    public static City.Distance getDistance(City from, City to, List<DirectionDelivery> directions){

        Set<City> cities = directions.stream().collect(getCityFromDirectionsCollector());

        Map<City, Integer> cityIntegerMap = new HashMap<>();
        Map<Integer, City> integerCityMap = new HashMap<>();

        int index = 0;
        for (City city:cities) {
            cityIntegerMap.put(city, index);
            integerCityMap.put(index, city);
            ++index;
        }

        double[][] graph = createGraph(directions, cityIntegerMap);

        Integer source = cityIntegerMap.get(from);
        Integer dest = cityIntegerMap.get(to);

        Dijkstra dijkstra = new Dijkstra(graph, source);
        double minDistance = dijkstra.calculateMinDistance(dest);

        if (minDistance != Double.POSITIVE_INFINITY){
            List<City> route = buildCitiesSmallestRout(dijkstra.buildShortestRoute(dest), integerCityMap);
            return new City.Distance(from, to, minDistance, route);
        }

        return null;
    }

    private static double[][] createGraph(List<DirectionDelivery> directions, Map<City, Integer> cityIntegerMap) {
        int size = cityIntegerMap.size();
        double[][] graph = new double[size][size];

        for (int i = 0; i < graph.length; i++) {
            Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
        }
        for (DirectionDelivery d : directions) {
            int i = cityIntegerMap.get(d.getSenderCity());
            int j = cityIntegerMap.get(d.getReceiverCity());
            graph[i][j] = graph[j][i] = d.getDistance();
        }
        return graph;
    }

    private static List<City> buildCitiesSmallestRout(List<Integer> routAsInt, Map<Integer, City> integerCityMap){
        return routAsInt.stream().map(integerCityMap::get).collect(Collectors.toList());
    }

    private static Collector<DirectionDelivery, HashSet<City>, HashSet<City>> getCityFromDirectionsCollector() {
        return Collector.of(
                (Supplier<HashSet<City>>) HashSet::new,
                (set, d) -> {
                    set.add(d.getSenderCity());
                    set.add(d.getReceiverCity());
                }, (cities1, cities2) -> {
                    cities1.addAll(cities2);
                    return cities1;
                }
        );
    }

}