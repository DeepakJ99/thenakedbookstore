var liked = false;

$(document).ready(function(){
    $("#like-button").click(function(){
        liked = !liked;
        if(liked){
            $(".heart-button").attr("src","public/external/heart_filled.png").addClass("animate__animated animate__bounceIn");
        }
        else{
            $(".heart-button").attr("src","public/external/heart_line.png").removeClass("animate__animated animate__bounceIn");
        }
    })

    $("#comments-close").click(function(){
        $("#comments-container").removeClass("animate__animated animate__fadeIn").hide();
        $("#comments-tray").removeClass("animate__animated animate__slideInUp").hide();
    })
    $("#comments-open").click(function(){
        $("#comments-container").addClass("animate__animated animate__fadeIn").show();
        $("#comments-tray").addClass("animate__animated animate__slideInUp").show();
    })

})