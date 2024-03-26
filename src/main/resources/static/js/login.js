new WOW().init();

$('.popup').hide();

$('.right').click(function () {
  $('.popup').show();
  $('.right').hide();
  $('.signUp').addClass('addClass');
  $('.loginContainer').css('height', '700px');
  $('.popup').css('height', 'auto');
  $('.signUp').css('height', '700px');
});

$('.close').click(function () {
  $('.popup').hide();
  $('.right').show();
  $('.signUp').removeClass('addClass');
  $('.loginContainer').css('height', '500px');
  $('.popup').css('height', '500px');
  $('.signUp').css('height', '500px');
});

$('.wrongUser').css('opacity', '0');
