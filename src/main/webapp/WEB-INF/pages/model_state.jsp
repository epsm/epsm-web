<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Model state</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/model_state.css"/>">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="all">
            <div class="roof">
                <div class="EPSsw">
                    <h2 class="EPS">Electric Power System Model</h2>
                </div>
                <form class="out" method="post" action="index.html">
                    <input class="input" type="submit" value="sign out" name="sign out" disabled />
                </form>
                <p class="cms">curent model state</p>
            </div>
            <div class="menu">
                <ul>
                	<li><a href="${dispatcherUrl}/app/history">dispatcher</a></li>
                </ul>
            </div>
            <div class="basement">
                <a href="https://github.com/epsm">project on GitHub</a>
            </div>
            <div class="indicators">
					<p>date and time on server: <font color="black">${realTimeStamp}</font></p>               
                	<p>date and time in simulation: <font color="black">${simulationTimeStamp}</font></p>
                	<p>frequency: <font color="black"><fmt:formatNumber type="number" pattern="0.000" value="${frequency}" /></font> Hz</p>
            	
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
									<p>generator#${generatorNumber} generation: 
											<fmt:formatNumber type="number" pattern="0.000" 
											value="${stationState.getGeneratorState(generatorNumber).generationInWM}" /> MW<p>
								</c:forEach>
								<br>
                        	</c:forEach>
                        </div>
                    </td>
                    <td>
                    	<div class="over">
							<c:forEach var="consumerState" items="${consumerStatesContainer}">
								<p>consumer#${consumerState.powerObjectId}</p>
								<p>load: <fmt:formatNumber type="number" pattern="0.000" value="${consumerState.load}" /> MW</p>
	                       		<br>
                        	</c:forEach>
                        </div>
                    </td>
                </tr>                  
            </table>
        </div>
    </body>
</html>
