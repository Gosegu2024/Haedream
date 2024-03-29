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

// 프로젝트 추가 / 삭제
$(document).ready(function(){
        $('#deleteButton').click(function(){
          // 선택한 프로젝트 id들을 배열에 저장하기
          var selectedProjectIds = [];
          $('input[name="projectIds"]:checked').each(function(){
            selectedProjectIds.push($(this).val());
          });

          if(selectedProjectIds.length>0){
            $.ajax({
              url: '/project/delete',
              type: 'POST',
              data: {
                projectIds: selectedProjectIds
              },
              traditional: true, // jQuery가 배열을 쿼리 스트링으로 올바르게 인코딩
              success:function(response){// DB에서 삭제되면 UI에서도 제거
                $('input[name="projectIds"]:checked').closest('li').remove();
              },
              error: function(){
                alert('프로젝트 삭제 실패');
              }
            });
          }
            else{
              alert('삭제할 프로젝트를 선택해주세요.');
            }
        });
      });