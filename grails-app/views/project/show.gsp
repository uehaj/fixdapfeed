<%@ page import="org.jggug.fixdapfeed.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Project</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Project List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Project</g:link></span>
        </div>
        <div class="body">
            <h1>Show Project</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Project:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'projectName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">User:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:projectInstance, field:'user')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Password:</td>
                            
                            <td valign="top" class="value"><%--${fieldValue(bean:projectInstance, field:'password')}--%>***</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Url:</td>
                            
                            <td valign="top" class="value"><a href="${projectInstance.projectUrl}">${projectInstance.projectUrl}</a></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">RSS Url:</td>
                            
                            <td valign="top" class="value"><a href="${projectInstance.rssUrl}">${projectInstance.rssUrl}</a></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Google Gadget Url:</td>
                            
                            <td valign="top" class="value"><a href="${projectInstance.gadgetUrl}">${projectInstance.gadgetUrl}</a></td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">HTML:</td>
                            
                            <td valign="top" class="value"><a href="${projectInstance.htmlUrl}">${projectInstance.htmlUrl}</a></td>
                            
                        </tr>

                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${projectInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
