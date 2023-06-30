import styled from 'styled-components';

export const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
`;

export const Container = styled.div`
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  position: relative;
  overflow: hidden;
  width: 100%;
  max-width: 768px;
  min-height: 480px;
`;

export const Overlay = styled.div`
  background: #CDDBF6;
  background: -webkit-linear-gradient(to right, #6478B3, #CDDBF6);
  background: linear-gradient(to right, #6478B3, #CDDBF6);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0 0;
  color: #FFFFFF;
  position: absolute;
  top: 0;
  height: 100%;
  width: 100%;
`;

export const OverlayPanel = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0;
  text-align: center;
  height: 100%;
  width: 100%;
`;

export const GhostButton = styled.button`
  border-radius: 20px;
  border: 1px solid #CDDBF6;
  background-color: transparent;
  color: #FFFFFF;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  letter-spacing: 1px;

  &:focus {
    outline: none;
  }
`;