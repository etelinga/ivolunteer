<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.actionfeed.Constants" %>
<fb:fbml version="1.1">
    <style>
        .vol_normal {
            color: rgb( 33, 33, 255 );
            font-size: 14pt;
        }
        .vol_img {
            float: right;
            margin: 0 0 15px 20px;
        }
    </style>
    <fb:dashboard>
        <fb:help href="help.xhtml">Help</fb:help>
        <fb:create-button href="<%=Constants.FACEBOOK_INSTALL_URL%>">Add Application</fb:create-button>
        <fb:action href="<%=Constants.FACEBOOK_LOGIN_URL%>">Try it</fb:action>
    </fb:dashboard>

    <div class="vol_normal">
        The iVolunteer application makes it easy for you and your friends to find ways to make a difference in
        your community.
    </div>
</fb:fbml>
