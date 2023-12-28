import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders App component', () => {
  // App 컴포넌트 렌더링
  render(<App />);
  
  // 예상하는 요소 또는 특정 조건을 확인하는 코드를 작성
  // ex. 특정 텍스트가 렌더링되는지 확인하는 코드
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
