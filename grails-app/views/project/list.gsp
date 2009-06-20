<%@ page import="org.jggug.fixdapfeed.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Project List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Project</g:link></span>
        </div>
        <div class="body">
            <h1>Project List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="project" title="Project" />
                        
                   	        <g:sortableColumn property="user" title="User" />
                        
                   	        <g:sortableColumn property="password" title="Password" />
                        
                   	        <g:sortableColumn property="url" title="Base Url" />

                   	        <g:sortableColumn property="mail" title="owner mail" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${projectInstanceList}" status="i" var="projectInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${projectInstance.id}">${fieldValue(bean:projectInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:projectInstance, field:'projectName')}</td>
                        
                            <td>${fieldValue(bean:projectInstance, field:'user')}</td>
                        
                            <td><%--${fieldValue(bean:projectInstance, field:'password')}--%>***</td>
                        
                            <td>${fieldValue(bean:projectInstance, field:'url')}</td>

                            <td>${fieldValue(bean:projectInstance, field:'ownerMail')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${projectInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
