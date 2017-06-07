/**
 * Created by vladimir_antin on 7.6.17..
 */
$(document).ready(function() {
    console.log("ready!");

    var message = $("#message");
    var login = $("#div_login");
    var info = $("#div_info");

    var username = $("#username");
    var password = $("#password");

    var myapp_user = localStorage.getItem("myapp_user");
    if(myapp_user===null){
        login.show();
        document.title="login";
    }else{
        getMe();
    }
    $("#login_btn").on('click', function(event) {
        event.preventDefault();
        var login_user =  {
            "username":username.val(),
            "password":password.val()
        };
        $.ajax({
            url:"http://localhost:8080/login",
            type:"POST",
            data:JSON.stringify(login_user),
            contentType:"application/json",
            dataType:"json",
            success: function(response){
                    localStorage.setItem("myapp_user","myapp_"+response.id);//id of user
                    me = localStorage.getItem("myapp_user");
                    setMe(response)
            },
            error:function () {
                message.text("error");
                message.show();
                setInterval(function(){ message.hide(); }, 3000);
            }
        });
    });

    $("#me").on('click', function(event) {
        event.preventDefault();
        getMe();
    });
    $("#logout").on("click",function (event) {
        event.preventDefault();
        localStorage.removeItem("myapp_user");
        login.show();
        info.hide();
        username.val("");
        username.focus();
        password.val("");

    });

    function getMe() {
        token = localStorage.getItem("myapp_user");
        $.ajax({
            url:"http://localhost:8080/api/me:"+token,
            type:"GET",
            contentType:"application/json",
            dataType:"json",
            success: function(response){
                setMe(response);
                document.title="info";
                message.text("role is: "+response.role);
                message.show();
                setInterval(function(){ message.hide(); }, 3000);
            }
        });
    }
    function setMe(me) {
        $("#info_id").text(me.id);
        $("#info_email").text(me.email);
        $("#info_name").text(me.name);
        login.hide();
        info.show();
        document.title="info";
    }
});