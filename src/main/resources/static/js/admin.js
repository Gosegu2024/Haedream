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

// 페이지네이션
let listCount = $('.userList > li').length;

const count_per_page = 8;

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
