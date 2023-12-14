import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import GlobalStyles from './styles/GlobalStyle';
import App from './App';

const root = createRoot(document.getElementById('root'));
root.render(
  <StrictMode>
    <GlobalStyles />
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
);
