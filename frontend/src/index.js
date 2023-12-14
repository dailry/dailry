import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import GlobalStyle from './styles/GlobalStyle';
import App from './App';

const root = createRoot(document.getElementById('root'));
root.render(
  <StrictMode>
    <GlobalStyle />
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
);
