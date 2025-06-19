// components/TabbedWindow.tsx
import React, { useState, type ReactNode } from "react";
import styled from "styled-components";

interface Tab {
  label: string;
  content: ReactNode;
}

interface TabbedWindowProps {
  tabs: Tab[];
}

const TabContainer = styled.div`
  width: 100%;
`;

const TabHeaders = styled.div`
  display: flex;
  border-bottom: 2px solid #ccc;
`;

const TabButton = styled.button<{ active: boolean }>`
  padding: 1rem 2rem;
  background: none;
  border: none;
  border-bottom: 3px solid
    ${({ active }) => (active ? "var(--color-brand-500)" : "transparent")};
  font-weight: ${({ active }) => (active ? "bold" : "normal")};
  cursor: pointer;
  color: ${({ active }) => (active ? "var(--color-brand-700)" : "#666")};
  transition: border-color 0.3s;
`;

const TabContent = styled.div`
  padding: 2rem 0;
`;

const TabbedWindow: React.FC<TabbedWindowProps> = ({ tabs }) => {
  const [activeIndex, setActiveIndex] = useState(0);

  return (
    <TabContainer>
      <TabHeaders>
        {tabs.map((tab, index) => (
          <TabButton
            key={tab.label}
            active={index === activeIndex}
            onClick={() => setActiveIndex(index)}
          >
            {tab.label}
          </TabButton>
        ))}
      </TabHeaders>
      <TabContent>{tabs[activeIndex].content}</TabContent>
    </TabContainer>
  );
};

export default TabbedWindow;
