import Planes.ExperimentalPlane;
import Planes.MilitaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class AirportTest {
    private static List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalTypes.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalTypes.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private static PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    private Airport airport = new Airport(planes);

    @Test
    public void testGetTransportMilitaryPlanes() {
        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();
        boolean result = transportMilitaryPlanes.stream().allMatch(item -> {
            Logger.getAnonymousLogger().info("Check that object" + item + " equals to Military transport");
            return item.getType().equals(MilitaryType.TRANSPORT);
        });
        Assert.assertTrue(result);
    }

    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        PassengerPlane expectedPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        Assert.assertEquals(expectedPlaneWithMaxPassengersCapacity.getPassengersCapacity(), planeWithMaxPassengerCapacity.getPassengersCapacity());
    }

    @Test
    public void testPlanesSortedByMaxLoadCapacity() {
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();
        boolean isSorted = IntStream.range(1, planesSortedByMaxLoadCapacity.size())
                .allMatch(index -> {
                    Logger.getAnonymousLogger().info("Check that object" + planesSortedByMaxLoadCapacity.get(index - 1) + " has MaxLoadCapacity less than next object");
                    return planesSortedByMaxLoadCapacity.get(index - 1).getMaxLoadCapacity() <= planesSortedByMaxLoadCapacity.get(index).getMaxLoadCapacity();
                });
        Assert.assertTrue(isSorted);
    }

    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() {
        List<MilitaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();
        Assert.assertTrue(bomberMilitaryPlanes.size() > 0);
    }

    @Test
    public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified() {
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean result = experimentalPlanes.stream().allMatch(item -> {
            Logger.getAnonymousLogger().info("Check that object" + item + " not equals to Classification level - UNCLASSIFIED");
            return !item.getType().equals(ClassificationLevel.UNCLASSIFIED);
        });
        Assert.assertTrue(result);
    }
}
