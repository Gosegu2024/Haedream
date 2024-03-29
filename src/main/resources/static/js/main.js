$('.projectList > li input').hide();
$('.delete button:nth-child(2)').hide();
$('.delete button:nth-child(3)').hide();

$('.delete button:first-child').click(function () {
  $('.delete button:nth-child(2)').show();
  $('.delete button:nth-child(3)').show();
  $('.delete button:first-child').hide();
  $('.projectList > li input').show();
  $('.projectInfo').hide();
});

$('.delete button:last-child').click(function () {
  $('.delete button:nth-child(2)').hide();
  $('.delete button:nth-child(3)').hide();
  $('.delete button:first-child').show();
  $('.projectList > li input').hide();
});

$('.projectInfo').hide();
$('.projectAdd > button').click(function () {
  $('.projectInfo').show();
  $('.delete button:nth-child(2)').hide();
  $('.delete button:nth-child(3)').hide();
  $('.projectList > li input').hide();
});

$('.back').click(function () {
  $('.projectInfo').hide();
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
