# Toy Robot Simulator

## Requirements

### Description

* The application is a simulation of a toy robot moving on a square tabletop,
  of dimensions 5 units x 5 units.
* There are no other obstructions on the table surface.
* The robot is free to roam around the surface of the table, but must be
  prevented from falling to destruction. Any movement that would result in the
  robot falling from the table must be prevented, however further valid
  movement commands must still be allowed.

Create an application that can read in commands of the following form:

    PLACE X,Y,F
    MOVE
    LEFT
    RIGHT
    REPORT

* PLACE will put the toy robot on the table in position X,Y and facing NORTH,
  SOUTH, EAST or WEST.
* The origin (0,0) can be considered to be the SOUTH WEST most corner.
* The first valid command to the robot is a PLACE command, after that, any
  sequence of commands may be issued, in any order, including another PLACE
  command. The application should discard all commands in the sequence until
  a valid PLACE command has been executed.
* MOVE will move the toy robot one unit forward in the direction it is
  currently facing.
* LEFT and RIGHT will rotate the robot 90 degrees in the specified direction
  without changing the position of the robot.
* REPORT will announce the X,Y and F of the robot. This can be in any form,
  but standard output is sufficient.

* A robot that is not on the table can choose the ignore the MOVE, LEFT, RIGHT
  and REPORT commands.
* Input can be from a file, or from standard input, as the developer chooses.
* Provide test data to exercise the application.

### Constraints

* The toy robot must not fall off the table during movement. This also
  includes the initial placement of the toy robot.
* Any move that would cause the robot to fall must be ignored.

### Example Input and Output

#### Example a

    PLACE 0,0,NORTH
    MOVE
    REPORT

Expected output:

    0,1,NORTH

#### Example b

    PLACE 0,0,NORTH
    LEFT
    REPORT

Expected output:

    0,0,WEST

#### Example c

    PLACE 1,2,EAST
    MOVE
    MOVE
    LEFT
    MOVE
    REPORT

Expected output

    3,3,NORTH

### Deliverables

Please provide your source code, and any test code/data you using in
developing your solution.

Please engineer your solution to a standard you consider suitable for
production. It is not required to provide any graphical output showing the
movement of the toy robot.

## Solution

### Stack

I knew that only a command line tool was required but I decided to put a very basic web command line tool together so the assessors did not have to do any set up to review the functioning of the application.

* Java 8
* Spring Boot
* Angular 1
* Angular Material
* Rest Web Service with HATEOAS
* Spring Data, JPA and Hibernate
* JUnit & Maven
* Amazon AWS

### Setup Instructions
There are no set up instruction as the application is deployed on AWS: [Toy Robot Web Interface](http://sample-env-1.z3tsumgpxk.us-west-2.elasticbeanstalk.com/)

The code has been documented with Javadocs and the source code exists on Github at: [Source Code](https://github.com/justiniantaylor/playground/tree/master/robot)
