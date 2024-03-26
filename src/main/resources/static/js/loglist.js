let listCount = $('.logList > li').length;

const count_per_page = 5;

let getTotalPageCount = () => {
  return Math.ceil(listCount / count_per_page);
};

const numberButtonWrapper = document.querySelector('.number-button-wrapper');

const setPageButtons = () => {
  numberButtonWrapper.innerHTML = '';

  for (let i = 1; i <= getTotalPageCount(); i++) {
    numberButtonWrapper.innerHTML += `<span class="number-button">${i}</span>`;
  }
};

setPageButtons();
