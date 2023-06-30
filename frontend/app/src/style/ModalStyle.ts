import styled, { keyframes } from 'styled-components';

const celebration = keyframes`
  0% { color: gold; }
  50% { color: red; }
  100% { color: gold; }
`;

export const AnimationDiv = styled.div`
    font-size: 2em;
    animation: ${celebration} 1s infinite;
`;

export const ModalBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const ModalContainer = styled.div`
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 80%;
  max-width: 500px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
`;