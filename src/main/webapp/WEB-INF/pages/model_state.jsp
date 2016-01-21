<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Model state</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/model_state.css">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="all">
            <div class="roof">
                <div class="EPSsw">
                    <h2 class="EPS">Electric Power System</h2>
                </div>
                <form class="out" method="post" action="index.html">
                    <input class="input" type="submit" value="sign out" name="sign out" disabled />
                </form>
                <p class="cms">curent model state</p>
            </div>
            <div class="menu">
                <ul>
                	<li><a href="${pageContext.request.contextPath}/model_logs">model logs</a></li>
                	<li><a href="${dipatcherUrl}/history">dispatcher</a></li>
                	<li><a href="${dipatcherUrl}/logs">dispatcher logs</a></li>
                </ul>
            </div>
            <div class="basement">
                <a href="https://github.com/epsm">project on GitHub</a>
            </div>
            <div class="indicators">
				<p>real time: ${realTimeStamp}</p>               
                <p>simulation time: ${simulationTimeStamp}</p>
                <p>frequency: ${frequency} Hz</p>
            </div>
            <table class="user_table">
            	<tr>
                    <th>power stations:</th>
                    <th>consumers:</th>
                </tr>
                <tr>
                    <td>
                    	<div class="over">
                    		<c:forEach var="stationState" items="${powerStationStatesContainer}">
								<p>power station#${stationState.powerObjectId}</p>
								<c:forEach var="generatorNumber" items="${stationState.generatorsNumbers}">
									<p>generator#${generatorNumber} generation ${stationState.getGeneratorState(generatorNumber).generationInWM} MW<p>
								</c:forEach>
								<br>
                        	</c:forEach>
                        </div>
                    </td>
                    <td>
                    	<div class="over">
							<c:forEach var="consumerState" items="${consumerStatesContainer}">
								<p>consumer#${consumerState.powerObjectId}</p>
								<p>load: ${-consumerState.load} MW</p>
	                       		<br>
                        	</c:forEach>
                        </div>
                    </td>
                </tr>                  
            </table>
        </div>
    </body>
</html>