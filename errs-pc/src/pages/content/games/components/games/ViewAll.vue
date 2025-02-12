<style scoped>
  .button1 {
    line-height: 1;
    text-decoration: none;
    display: inline-flex;
    align-items: center;
    gap: 0.75rem;
    background-color: #000;
    color: #fff;
    border-radius: 10rem;
    font-weight: 600;
    padding: 0.75rem 1.5rem;
    padding-left: 20px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    transition: background-color 0.3s;
  }

  .button1__icon-wrapper {
    flex-shrink: 0;
    width: 25px;
    height: 25px;
    position: relative;
    color: #000;
    background-color: #fff;
    border-radius: 50%;
    display: grid;
    place-items: center;
    overflow: hidden;
  }

  .button1:hover {
    background-color: #fff;
    color: black;
  }

  .button1:hover .button1__icon-wrapper {
    color: #fff;
    background-color: #000;
  }

  .button1__icon-svg--copy {
    position: absolute;
    transform: translate(-150%, 150%);
  }

  .button1:hover .button1__icon-svg:first-child {
    transition: transform 0.3s ease-in-out;
    transform: translate(150%, -150%);
  }

  .button1:hover .button1__icon-svg--copy {
    transition: transform 0.3s ease-in-out 0.1s;
    transform: translate(0);
  }
</style>

<template>
  <a class="button1" href="#" style="--clr: #FFF">
    <span class="button1__icon-wrapper">
      <svg
        class="button1__icon-svg"
        fill="none"
        viewBox="0 0 14 15"
        width="10"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M13.376 11.552l-.264-10.44-10.44-.24.024 2.28 6.96-.048L.2 12.56l1.488 1.488 9.432-9.432-.048 6.912 2.304.024z"
          fill="currentColor"
        ></path>
      </svg>

      <svg
        class="button1__icon-svg button1__icon-svg--copy"
        fill="none"
        viewBox="0 0 14 15"
        width="10"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M13.376 11.552l-.264-10.44-10.44-.24.024 2.28 6.96-.048L.2 12.56l1.488 1.488 9.432-9.432-.048 6.912 2.304.024z"
          fill="currentColor"
        ></path>
      </svg>
    </span>
    查看更多
  </a>
</template>
