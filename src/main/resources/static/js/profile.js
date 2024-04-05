$('.speech-bubble').hide();

const speechBubble = document.querySelector('.speech-bubble');
let text = $('.apikey').text();
console.log(text);

const copyClipboard = () => {
  $('.speech-bubble').fadeIn();
  setTimeout(() => {
    $('.speech-bubble').fadeOut();
  }, 2000);
  try {
    navigator.clipboard.writeText(text);
  } catch {
    const textArea = document.createElement('textarea');
    textArea.value = text;
    document.body.appendChild(textArea);
    textArea.select();
    textArea.setSelectionRange(0, 99999);
    document.execCommand('copy');
    textArea.setSelectionRange(0, 0);
    document.body.removeChild(textArea);
  }
};

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