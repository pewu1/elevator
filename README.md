This program simulates elevator management system in a tower.
User is able to configure number of elevators, number of floors, and mode of operation (whether it is possible to travel between any floors, or only from ground floor up or down to ground floor). Configuration is done by editing static fields in ElevatorUtil class.

Please note that when there is a request in the queue and a particular elevator passes the request's from and to floors in the same direction, it is assumed that the elevator stops at those floor and the request is marked as done (cleared from queue), even though stopping the elevator at those floors is not fully simulated.

Program runs best in windows CLI as it attempts to clear screen at intervals which is often not possible inside IDE.

