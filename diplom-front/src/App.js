import React, { useState, useCallback } from "react";
import ReactDOM from 'react-dom/client';
import NearestBranches from "./components/NearestBranches/NearestBranches";
import Branch from "./components/Branch/Branch";
import BranchListMap from "./components/BranchListMap/BranchListMap"
import { BrowserRouter, Route, Routes } from "react-router";
import PopularServices from "./components/PopularServices/PopularServices";
import MainPage from "./pages/MainPage/MainPage";
import BranchPage from "./pages/BranchPage/BranchPage";
import Header from "./components/Header/Header";
import ServiceList from "./components/ServiceList/ServiceList";


function App() {
  const [branchId, setBranchId] = useState()

  const handleSetBranchId = useCallback((id) => {
    setBranchId(id);
  }, []);

  return (
    <div>
      {/* <Header/> */}
      {/* <ServiceList page={0} size={2}/> */}
      <BrowserRouter>
        <Header/>
        <Routes>
          <Route path="/" element={<MainPage/>}/>
          <Route path="/branches" element={<BranchListMap branchId={ branchId }/>}/>
          <Route path=":branchId" element={<BranchPage/>}/>
          <Route path="/services" element={<PopularServices/>}/>
        </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;
