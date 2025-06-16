import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
/* 
Font sizes:
10 12 14 16 18 20 24 30 36 44 52 62 74 86 98

Spaces:
2 4 8 12 16 24 32 48 64 80 96 128

Font weights:
Default: 400
Medium: 500
Semi-Bold: 600
Bold: 700
*/

.rdg-cell{
  overflow-y: auto;
}

:root {

  scroll-behavior: smooth;

  --color-grey-fixed-white: #f9fafb;
  --color-grey-fixed-light: #e5e7eb;
  --color-grey-fixed-dark: #374151;
  --color-brand-fixed-medium: #d35400;
  --color-background-grey-opaque-fixed-light: rgb(229, 231, 235, 0.8);

  --border-radius-tiny: 3px;
  --border-radius-sm: 5px;
  --border-radius-md: 7px;
  --border-radius-lg: 9px;
  
  &, &.light-mode{
    --color-brand-50: #fbeee6;
    --color-brand-100: #f2ccb3;
    --color-brand-200: #e59866;
    --color-brand-500: #dc7633;
    --color-brand-600: #d35400;
    --color-brand-700: #943b00;
    --color-brand-800: #542200;
    --color-brand-900: #150800;

    --color-grey-0: #fff;
    --color-grey-50:rgb(242, 242, 242);
    --color-grey-100:rgb(245, 245, 245);
    --color-grey-200:rgb(233, 233, 233);
    --color-grey-300:rgb(215, 215, 215);
    --color-grey-400:rgb(174, 174, 174);
    --color-grey-500:rgb(123, 123, 123);
    --color-grey-600:rgb(101, 101, 101);
    --color-grey-700:rgb(82, 82, 82);
    --color-grey-800:rgb(54, 54, 54);
    --color-grey-900:rgb(35, 35, 35);

    --color-blue-100: #e0f2fe;
    --color-blue-700: #0369a1;
    --color-green-100: #dcfce7;
    --color-green-700: #15803d;
    --color-yellow-100: #fef9c3;
    --color-yellow-700: #a16207;
    --color-silver-100: #e5e7eb;
    --color-silver-700: #374151;
    --color-indigo-100: #e0e7ff;
    --color-indigo-700: #4338ca;

    --color-red-100: #fee2e2;
    --color-red-700: #b91c1c;
    --color-red-800: #991b1b;

    --color-highlight:rgb(207, 9, 9);

    --backdrop-color: rgba(255, 255, 255, 0.1);

    --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.04);
    --shadow-md: 0px 0.6rem 2.4rem rgba(0, 0, 0, 0.06);
    --shadow-lg: 0 2.4rem 3.2rem rgba(0, 0, 0, 0.12);


    --image-grayscale: 0;
    --image-opacity: 100%;

  }

  &, &.dark-mode{
    --color-brand-900: #fbeee6;
    --color-brand-800: #f2ccb3;
    --color-brand-700: #e59866;
    --color-brand-600: #dc7633;
    --color-brand-500: #d35400;
    --color-brand-200: #943b00;
    --color-brand-100: #542200;
    --color-brand-50: #150800;

    --color-grey-0:rgb(32, 32, 32);
    --color-grey-50:rgb(37, 37, 37);
    --color-grey-100:rgb(55, 55, 55);
    --color-grey-200:rgb(86, 86, 86);
    --color-grey-300:rgb(96, 96, 96);
    --color-grey-400:rgb(127, 127, 127);
    --color-grey-500:rgb(174, 174, 174);
    --color-grey-600:rgb(218, 218, 218);
    --color-grey-700:rgb(235, 235, 235);
    --color-grey-800:rgb(246, 246, 246);
    --color-grey-900:rgb(255, 255, 255);

    --color-blue-100: #075985;
    --color-blue-700: #e0f2fe;
    --color-green-100: #166534;
    --color-green-700: #dcfce7;
    --color-yellow-100: #854d0e;
    --color-yellow-700: #fef9c3;
    --color-silver-100: #374151;
    --color-silver-700: #f3f4f6;
    --color-indigo-100: #3730a3;
    --color-indigo-700: #e0e7ff;

    --color-red-100: #fee2e2;
    --color-red-700: #b91c1c;
    --color-red-800: #991b1b;

    --color-highlight:rgb(231, 235, 17);

    --backdrop-color: rgba(0, 0, 0, 0.3);

    --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.4);
    --shadow-md: 0px 0.6rem 2.4rem rgba(0, 0, 0, 0.3);
    --shadow-lg: 0 2.4rem 3.2rem rgba(0, 0, 0, 0.4);

    --image-grayscale: 10%;
    --image-opacity: 90%;
  }
}

*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;

  transition: background-color 0.3s, border 0.3s;
}

*:disabled{
  cursor: not-allowed;
}

html {
  font-size: 62.5%;
  box-sizing: border-box;
}

body {
  font-family: "Rubik", sans-serif;
  color: var(--color-grey-700);
  background-color: var(--color-grey-50);

  transition: background-color 0.3s, color 0.3s;
  line-height: 1.5;
  min-height: 100vh;
  font-size: 1.6rem;
}

input,
button,
textarea,
select {
  font: inherit;
  color: inherit;
}

input:focus,
button:focus,
textarea:focus,
select:focus {
  outline: 2px solid var(--color-brand-600);
  outline-offset: -1px;
}

button {
  cursor: pointer;
}

button:has(svg) {
  line-height: 0;
}

a {
  color: inherit;
  text-decoration: none;
}

ul {
  list-style: none;
}

p,
h1,
h2,
h3,
h4,
h5,
h6 {
  overflow-wrap: break-word;
  hyphens: auto;
}

img {
  max-width: 100%;
  filter: grayscale(var(--image-grayscale)) opacity(var(--image-opacity));
}

::-webkit-scrollbar {
  width: 6pt; /* Set the width of the scrollbar */
}

::-webkit-scrollbar-thumb {
  background-color: var(--color-brand-700); 
  border-radius: var(--border-radius-sm);
}

::-webkit-scrollbar-track {
  background-color: var(--color-grey-400); /* Set the color of the track */
  border-radius: var(--border-radius-sm);
}

::-webkit-scrollbar-thumb:hover {
  background-color: var(--color-brand-500);
}

`;

export default GlobalStyles;
