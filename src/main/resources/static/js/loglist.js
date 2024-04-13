// 페이지네이션
let listCount = $('.logList > li').length;

const count_per_page = 5;

let getTotalPageCount = () => {
  return Math.ceil(listCount / count_per_page);
};

const numberButtonWrapper = document.querySelector('.number-button-wrapper');

const setPageButtons = () => {
  numberButtonWrapper.innerHTML = '';

  for (let i = 1; i <= getTotalPageCount(); i++) {
    numberButtonWrapper.innerHTML += `<span class="number-button pageActive">${i}</span>`;
  }
};

setPageButtons();

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

        $(document).ready(function () {
            $.ajax({
                url: '/logListData', 
                type: 'GET',
                dataType: 'json', 
                success: function (logs) {
                    var logListHtml = '';
                    $.each(logs, function (index, log) {
                        logListHtml += '<li><p class="index">' + (
                            index + 1
                        ) + '.</p><div class="mid"><div class="projectName">' + log.projectName + '</di' +
                                'v><span class="modelName">' + log.modelName +
                                '</span><span class="logDate">(' + log.logDate + ')</span></div><div class="del' +
                                '"><i class="fa-solid fa-trash-can"></i></div></li>';
                    });
                    $('.logList').html(logListHtml);
                },
                
            });
        });

        $(document).ready(function () {
            $('.del-button').on('click', function () {
                var logId = $(this).attr('data-logId');
                var logItem = $(this).closest('li');

                $.ajax({
                    url: '/logList/' + logId,
                    type: 'DELETE',
                    success: function (response) {
                        logItem.remove();

                        $('.logList li').each(function (index) {
                            $(this)
                                .find('.index')
                                .text(index + 1 + '.');
                        });
                    },
                    error: function () {
                        alert('로그 삭제 실패');
                    }
                });
            });
        });

        function bindDeleteButtons() {
            $('.del-button')
                .off('click')
                .on('click', function () {
                    var logId = $(this).data('logId');
                    $.ajax({
                        url: '/logList/' + logId,
                        type: 'DELETE',
                        success: function (response) {
                        },
                        error: function () {
                            alert('로그 삭제 실패');
                        }
                    });
                });
        }

        // 페이지 로드 시 초기 바인딩
        bindDeleteButtons();