import styled, { keyframes } from 'styled-components';

const spin = keyframes`
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
`;

export const Spinner = styled.div`
  margin: 100px auto;
  width: 50px;
  height: 50px;
  border: 6px solid #eee;
  border-top: 6px solid #3498db;
  border-radius: 50%;
  animation: ${spin} 1s linear infinite;
`;