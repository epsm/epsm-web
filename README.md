#Electric power system model
###General data for the project
This is the simple model of a dedicated electric power system. The model consist of two separate parts. There are model and dispatcher. In the model simulated working of power stations equipment, that participate in process of supporting the frequency in system.
The model gets daily generation schedules from dispatcher. Dispatcher gets data from power stations, consumers and calculates (for now gives stub) daily generation schedules.

Application consist of four packages, two models:

+ [epsm-core](https://github.com/epsm/epsm-core)
+ [epsd-core](https://github.com/epsm/epsd-core)

and two wrappers for models that helps intarcting models using JSON. Also wrappers have web interfaces.

+ [epsd-web](https://github.com/epsm/epsd-web)
+ [epsm-web](https://github.com/epsm/epsm-web)


Application launched on two servers on OpenShift:

+ [model](http://model-epsm.rhcloud.com/)
+ [dispatcher](http://dispatcher-epsm.rhcloud.com/app/history). 

The total project size is more than 16,000 source lines of code.

| technology    |  [epsm-core](https://github.com/epsm/epsm-core)    | [epsm-web](https://github.com/epsm/epsm-web)   | [epsd-core](https://github.com/epsm/epsd-core)| [epsd-web](https://github.com/epsm/epsd-web)|
|:-----------------|:---:|:---:|:---:|:---:|
| Java core        | yes | yes | yes | yes |
| Spring core      | no  | yes | no  | yes |
| MySQL            | no  | no  | no  | yes |
| JPA (Hibernate)  | no  | no  | no  | yes |
| JSP              | no  | yes | no  | yes |
| Spring webmvc    | no  | yes | no  | yes |
| JSON (fasterxml) | yes | yes | no  | yes |
| HTML, CSS        | no  | yes | no  | yes |
| Spring security  | no  | no  | no  | yes |
| SLF4J, Logback   | yes | yes | yes | yes |
| Junit, Mockito   | yes | yes | yes | yes |
| Power Mockito    | yes | yes | no  | no  |
| Spring test      | no  | yes | no  | yes |
| DbUnit           | no  | no  | no  | yes |

##epsm-web
#### package description
To understand what does project do you have to read following firstly:

1. subject area description for [epsm-core](https://github.com/epsm/epsm-core).
2. subject area description for [epsd-core](https://github.com/epsm/epsd-core)


epsm-web is one of the two servers of the distributed application. Inside the server running model of the power system from the package [epsm-core](https://github.com/epsm/epsm-core). The server performs the receiving and transmission of messages in JSON format over http for the model. 
Messaging is performing with the server [epsd-web](https://github.com/epsm/epsd-web), which  is a wrapper for the dispatcher of the electric power system from the package [epsd-core](https://github.com/epsm/epsd-core). Also the server has a [web interface](http://model-epsm.rhcloud.com/) that displays the state of the model at the time of the page request.

####realization

It will be better to see class diagrams in realization chapter of [epsm-core](https://github.com/epsm/epsm-core) to understand more clearly.

Inside the Spring container creates bean that implements the DispatchingObjectSource interface. This bean actually is a model of power system. Through this interface the another bean that is IncomingMessageService implementation passes given from controllers messages to model. Model sends messages to the dispatcher through the bean that implements the interface OutgoingMessageService, which inherited from Dsipatcher interface.

#### technologies
Spring webmvc, JSON, SLF4J, Logback, Junit, Mockito, PowerMockito and Spring test.

Unit-test coverage according to EclEmma is  85,9%.