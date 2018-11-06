//
// Created by avihirsch on 10/31/2018.
//

#include <stdio.h>
#include <stdlib.h>
#include "dataStore.h"

//declarations
size_t maxMemory = 0;
size_t maxCarsInMemory = 0;
int carsInMemory = 0;
int carsOnDisk = 0;

//pointer to memory holding cars.
Car* a;

//the disk
FILE* disk;

int writeToDisk(Car car) {
    printf("writing car to disk...\n");
    //write a car to disk
    Car* c = &car;                          //point to car
    disk = fopen("disk.txt", "ab");         //open file for writing to disk
    if(disk != NULL) {
        printf("id of car to be written to disk is: %d\n", c->uniqueID);
        fwrite(c, sizeof(Car), 1, disk);    //write car to disk
        fclose(disk);                       //close file
    }
    carsOnDisk++;
    printf("wrote car to disk. # of cars on disk is: %d\n", carsOnDisk);
    return 0;
}

int writeToMemory(Car car) {
    printf("Writing car to memory...\n");
    //if there is at least one car in memory, shift all cars in memory by one.
    if(carsInMemory) {
        Car prev = car, next;
        for (int i = 0; i < carsInMemory; ++i) {
            //shift each car.
            next = a[i];
            a[i] = prev;
            prev = next;
        }
        //if memory is full, move lru to disk.
        if (carsInMemory == maxCarsInMemory) {
            writeToDisk(prev);
        //if memory is not full, increase number of cars in memory.
        } else {
            ++carsInMemory;
        }
    //if there are no cars in memory, just add car to first slot.
    } else {
        a[0] = car;
        ++carsInMemory;
    }
    return 0;
}

int addCar(char* make, char* model, short year, long price, int uniqueID) {
    //initialize car to hold params
    Car car = {make, model, year, price, uniqueID};

    //if the max # of cars in memory >= 1, add the car to memory.
    if (maxCarsInMemory >= 1) {
        writeToMemory(car);
    //if max # of cars in memory < 1, write the car to disk.
    } else {
        writeToDisk(car);
    }

    return 0;
}
struct Car* getFirstCarFromDisk() {
    //open file for reading from disk.
    disk = fopen("disk.txt", "rb");

    //initialize pointers to matching car and buffer.
    Car* c = NULL;
    Car* buf = NULL;

    if (disk != NULL) {
        //begin at start of disk and return that car.
        fseek(disk, 0, SEEK_SET);
        buf = (Car *) malloc(sizeof(Car));
        fread(buf, sizeof(Car), 1, disk);
        c = buf;
    }
    //free buffer memory, close file.
    free(buf);
    fclose(disk);

    //if a car was found, make it the mru in memory (delete and write to memory)
    if (c != NULL) {
        deleteCarById(c->uniqueID);
        writeToMemory(*c);
    }
    return c;
}
struct Car* deleteLastCarFromMemory() {
    if(carsInMemory) {
        int i = carsInMemory - 1;
        Car *lru = &a[i];
        deleteCarById(lru->uniqueID);
        return lru;
    }
    return NULL;
}

int setMaxMemory(size_t bytes) {
    //keep track of maxMemory
    maxMemory = bytes;
    maxCarsInMemory = maxMemory / sizeof(Car);

    //if there is no memory allotted, allocate new memory and fill with cars from disk.
    if (maxMemory == 0) {
        a = (Car *) malloc(maxMemory);
        //move cars from disk to memory.
        while ((maxCarsInMemory > carsInMemory) && carsOnDisk > 0) {
            Car *toMove = getFirstCarFromDisk();
            deleteCarById(toMove->uniqueID);
            writeToMemory(*toMove);
        }
    }
    //else, check for changes (cars to be added or removed from memory) and reallocate.
    else {
        //while there is more room in memory AND there are cars on the disk,
        //move cars to memory until either the disk is empty or memory is full.
        if(maxCarsInMemory > carsInMemory && carsOnDisk > 0) {
            Car* b = (Car *) realloc(a, maxMemory);
            a = b;
            while ((maxCarsInMemory > carsInMemory) && carsOnDisk > 0) {
                Car *toMove = getFirstCarFromDisk();
                deleteCarById(toMove->uniqueID);
                writeToMemory(*toMove);
            }
        }
        //while there are more cars in memory than there is room in memory,
        //move lru cars to disk until max cars in memory == cars in memory.
        else if (carsInMemory > maxCarsInMemory) {
            while (carsInMemory > maxCarsInMemory) {
                Car *toMove = deleteLastCarFromMemory();
                writeToDisk(*toMove);
            }
            //now, reallocate memory.
            Car* b = (Car *) realloc(a, maxMemory);
            a = b;
        }
    }
    return 0;
}
struct Car* getCarFromMemoryById(int id) {
    for (int i = 0; i < carsInMemory; ++i) {
        if (a[i].uniqueID == id) {
            //becomes mru (delete, then add), then returns it.
            Car* found = &a[i];
            deleteCarById(id);
            writeToMemory(*found);
            return found;
        }
    }
    return NULL;
}
struct Car* getCarFromDiskById(int id) {
    //open file for reading from disk.
    disk = fopen("disk.txt", "rb");

    //initialize pointers to matching car and buffer.
    Car* c = NULL;
    Car* buf = NULL;

    if (disk != NULL) {
        //begin at start of disk and linearly search for car.
        fseek(disk, 0, SEEK_SET);
        for (int i = 0; i < carsOnDisk; ++i) {
            buf = (Car *) malloc(sizeof(Car));
            fread(buf, sizeof(Car), 1, disk);
            if (buf->uniqueID == id) {
                printf("id of car being read from disk is: %d\n", buf->uniqueID);
                c = buf;
                break;
            }
        }
    }
    //free buffer memory, close file.
    free(buf);
    fclose(disk);

    //if a car was found, make it the mru in memory (delete and write to memory)
    if (c != NULL && maxCarsInMemory) {
        deleteCarById(c->uniqueID);
        writeToMemory(*c);
    }
    return c;
}
struct Car* getCarById(int id){
    printf("Getting car %d...", id);

    //first, search cars in memory by id and return if found.
    Car* result = getCarFromMemoryById(id);
    if (result != NULL) {
        return result;
    }

    //if not in memory, search cars on disk and return.
    return getCarFromDiskById(id);
}
int deleteCarFromMemoryById(int id) {
    if(carsInMemory) {
        //Car* toDelete;
        for (int i = 0; i < carsInMemory; ++i) {
            //found car to delete
            if (a[i].uniqueID == id) {
                //toDelete = &a[i];
                //if there are more cars in memory, shift them left
                if ((carsInMemory - (i+1)) >= 1) {
                    for (int j = i; j < (carsInMemory - 1); ++j) {
                        a[j] = a[j + 1];
                    }
                } else {
                    //set to null
                }
                --carsInMemory;
                break;
            }
        }
    }
    return -1;
}
int deleteCarFromDiskById(int id) {
    //open file for reading from disk.
    disk = fopen("disk.txt", "rb+");

    //initialize pointers to matching car and buffer.
    Car* c = NULL;
    Car* buf = NULL;
    Car* buf2 = NULL;

    if (disk != NULL) {
        //begin at start of disk and linearly search for car to delete.
        fseek(disk, 0, SEEK_SET);
        for (int i = 0; i < carsOnDisk; ++i) {
            buf = (Car *) malloc(sizeof(Car));
            fread(buf, sizeof(Car), 1, disk);
            if (buf->uniqueID == id) {
                printf("id of car being deleted from disk is: %d\n", buf->uniqueID);
                for (int j = i; j < (carsOnDisk - 1); ++j) {
                    buf2 = (Car *) malloc(sizeof(Car));
                    //read, move back one Car, write, repeat.
                    fread(buf2, sizeof(Car), 1, disk);
                    fseek(disk, -1 * sizeof(Car), SEEK_CUR);
                    fwrite(buf2, sizeof(Car), 1, disk);
                }
                --carsOnDisk;
                break;
            }
        }
    }
    //free buffer memory, close file.
    free(buf);
    free(buf2);
    fclose(disk);
}
int deleteCarById(int id){
    //search cars in memory by id, remove it.
    if(deleteCarFromMemoryById(id)) {
        return 0;
    }
    //if not in memory, search cars on disk and remove.
    else if (deleteCarFromDiskById(id)){
        return 0;
    }
    else {
        //failed to delete car.
        return -1;
    }
}
int modifyCarById(int id, struct Car* myCar){
    printf("Modifying car %d...", id);

    //first, search cars in memory by id and modify if found.
    Car* result;
    result = getCarFromMemoryById(id);
    if (result != NULL) {
        //modify data at pointer result
        *result = *myCar;
        return 0;
    }
    //if not in memory, search cars on disk, modify, and return.
    else {
        result = getCarFromDiskById(id);
        if (result != NULL) {
            *result = *myCar;
            return 0;
        }
    }
    //failed to find Car.
    return -1;
}
int getNumberOfCarsInMemory(){
    return carsInMemory;
}
int getAmountOfUsedMemory(){
    return carsInMemory * sizeof(Car);
}
int getNumberOfCarsOnDisk(){
    return carsOnDisk;
}
struct Car* getAllCarsInMemory(){
    Car cars[carsInMemory];
    for (int i = 0; i < carsInMemory; ++i) {
        cars[i] = a[i];
    }
    Car* c = cars;
    return c;
}
struct Car* getAllCarsOnDisk() {
    printf("Getting all cars on disk...");

    //allocate memory for all cars on disk
    Car* carArray = malloc(carsOnDisk * sizeof(Car));

    disk = fopen("disk.txt", "rb");
    Car* buf = NULL;

    if(disk != NULL) {
        //begin at start of disk.
        fseek(disk, 0, SEEK_SET);
        for (int i = 0; i < carsOnDisk; ++i) {
            buf = (Car *) malloc(sizeof(Car));
            fread(buf, sizeof(Car), 1, disk);
            printf("id of car being read from disk is: %d\n", buf->uniqueID);
            carArray[i] = *buf;
        }
    }
    free(buf);
    return carArray;
}