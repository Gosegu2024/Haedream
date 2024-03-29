// menu
$('.dropDown').hide();
$('.menu > li:nth-child(2)').mouseover(() => {
  $('.dropDown').stop().slideDown();
});
$('.menu > li:nth-child(2)').mouseout(() => {
  $('.dropDown').stop().slideUp();
});

$('.popup > li:nth-child(3)').mouseover(() => {
  $('.dropDown').stop().slideDown();
});
$('.popup > li:nth-child(3)').mouseout(() => {
  $('.dropDown').stop().slideUp();
});

$('.popupBack').hide();
$('.bar').click(function () {
  $('.popupBack').show();
  $('.popup').animate({ left: '0' }, 500);
});
$('.popup > li:nth-child(1) i').click(function () {
  $('.popupBack').hide();
  $('.popup').animate({ left: '50%' }, 500);
});
$('.popupBack').click(function () {
  $('.popupBack').hide();
  $('.popup').animate({ left: '50%' }, 500);
});

// scrollTop

$('.scrollTop').hide();
$(window).scroll(function () {
  if ($(this).scrollTop() > 100) {
    $('.scrollTop').fadeIn();
  } else {
    $('.scrollTop').fadeOut();
  }
});

$('.scrollTop').click(function () {
  $('html, body').animate({ scrollTop: 0 }, '300');
});


