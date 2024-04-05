

// menu
$('.dropDown').hide();
$('.menu > li:nth-child(1)').mouseover(() => {
  $('.dropDown').stop().slideDown();
});
$('.menu > li:nth-child(1)').mouseout(() => {
  $('.dropDown').stop().slideUp();
});

$('.popup > li:nth-child(2)').mouseover(() => {
  $('.dropDown').stop().slideDown();
});
$('.popup > li:nth-child(2)').mouseout(() => {
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