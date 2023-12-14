import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
    *, *::before, *::after {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        font-family: Inter, system-ui, -apple-system, BlinkMacSystemFont, 'Open Sans', 'Helvetica Neue', sans-serif;
    }

    ul, li {
        list-style: none;
    }

    a, a:hover, a:active, a:visited {
        text-decoration: none;
        outline: none;
    }

    input:disabled {
        background: none;
        cursor: not-allowed;
    }

    button {
        background: none;
        border: 0;
        cursor: pointer;
    }

    button:disabled {
        cursor: not-allowed;
    }
`;
export default GlobalStyle;
