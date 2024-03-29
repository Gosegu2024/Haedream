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
