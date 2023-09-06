$(document).ready(function(){
    $("#addresses-close").click(function(){
        $("#addresses-container").removeClass("animate__animated animate__fadeIn").hide();
        $("#addresses").removeClass("animate__animated animate__slideInUp").hide();
    })
    $("#addresses-open").click(function(){
        $("#addresses-container").addClass("animate__animated animate__fadeIn").show();
        $("#addresses").addClass("animate__animated animate__slideInUp").show();
    })

    $("#close-edit-address").click(function(){
        $("#address-edit-container").removeClass("animate__animated animate__fadeIn").hide();
        $("#address-edit-main").removeClass("animate__animated animate__slideInUp").hide();
    })
    $(".edit-address").click(function(){
        $("#address-edit-container").addClass("animate__animated animate__fadeIn").show();
        $("#address-edit-main").addClass("animate__animated animate__slideInUp").show();
    })
    $("#add-new-address").click(function(){
        $("#address-edit-container").addClass("animate__animated animate__fadeIn").show();
        $("#address-edit-main").addClass("animate__animated animate__slideInUp").show();
    })
})

