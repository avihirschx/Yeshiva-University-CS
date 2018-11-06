#include <stdio.h>
#include <stdlib.h>
#include "dataStore.h"

int main() {


    //***TEST DISK***

    addCar("make1", "model1", 1000, 1000, 12345);
    addCar("make2", "model2", 2000, 2000, 123456);
    addCar("make3", "model3", 3000, 3000, 1234567);

    //declare their two ids.
    int id1 = 12345;
    int id2 = 123456;
    int id3 = 1234567;

    //get them by id and print returned cars.
    Car car1 = *getCarById(id1);
    Car car2 = *getCarById(id2);
    Car car3 = *getCarById(id3);
    printf("Third car returned is %s\n", car3.make);
    printf("First car returned is %s\n", car1.make);
    printf("Second car returned is %s\n", car2.make);

    //get all cars on disk, then, free the memory!
    Car* cars;
    cars = getAllCarsOnDisk();
    for (int i = 0; i < getNumberOfCarsOnDisk(); ++i) {
        printf("Car #%d has id %d\n", i+1, cars[i].uniqueID);
    }
    free(cars);

    //***END OF DISK TEST***

    //***TEST MEMORY***

    //set max memory to one car-size
    setMaxMemory(sizeof(Car));

    //get all cars in memory
    cars = getAllCarsInMemory();
    for (int i = 0; i < getNumberOfCarsInMemory(); ++i) {
        printf("Car #%d in memory has id %d\n", i+1, cars[i].uniqueID);
    }

    return 0;
}