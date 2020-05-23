 //pop up forma
 $(window).on('load',function () {
    $("#removeSchedule").click(function(){
       $('.hover_bkgr_fricc').show();
    });
    $('.popupCloseButton').click(function(){
        $('.hover_bkgr_fricc').hide();
    });
  });