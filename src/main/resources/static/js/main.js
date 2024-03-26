$('.projectList > li > input').hide();
$('.delete button:nth-child(2)').hide();
$('.delete button:nth-child(3)').hide();

$('.delete button:first-child').click(function () {
  $('.delete button:nth-child(2)').show();
  $('.delete button:nth-child(3)').show();
  $('.projectList > li > input').show();
});

$('.delete button:last-child').click(function () {
  $('.delete button:nth-child(2)').hide();
  $('.delete button:nth-child(3)').hide();
  $('.projectList > li > input').hide();
});

$('.projectInfo').hide();
$('.projectAdd > button').click(function () {
  $('.projectInfo').show();
});

$('.back').click(function () {
  $('.projectInfo').hide();
});
