<%@ page import="com.xwtxumm.xummnftwalletcreator.xumm" %>
<%
  xumm x = (xumm)session.getAttribute("xumm");
%>
<html lang="en"><head><base href="">
  <meta charset="utf-8">
  <title>XRP Wallet Tools</title>
  <meta name="description" content="XRP Wallet Tools, All in one platform manage XRP Wallet, Mint NFT and Sell/Buy NFT.">
  <meta name="keywords" content="XRP Wallet Tools">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="/metronic8/demo1/assets/media/logos/favicon.ico">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700">
  <link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css">
  <link href="assets/css/style.bundle.css" rel="stylesheet" type="text/css">
  <link href="assets/css/dashboard.css" rel="stylesheet" type="text/css">
  <style type="text/css">


.apexcharts-canvas {
    position: relative;
    user-select: none;
    /* cannot give overflow: hidden as it will crop tooltips which overflow outside chart area */
  }


  /* scrollbar is not visible by default for legend, hence forcing the visibility */
  .apexcharts-canvas ::-webkit-scrollbar {
    -webkit-appearance: none;
    width: 6px;
  }

  .apexcharts-canvas ::-webkit-scrollbar-thumb {
    border-radius: 4px;
    background-color: rgba(0, 0, 0, .5);
    box-shadow: 0 0 1px rgba(255, 255, 255, .5);
    -webkit-box-shadow: 0 0 1px rgba(255, 255, 255, .5);
  }


  .apexcharts-inner {
    position: relative;
  }

  .apexcharts-text tspan {
    font-family: inherit;
  }

  .legend-mouseover-inactive {
    transition: 0.15s ease all;
    opacity: 0.20;
  }

  .apexcharts-series-collapsed {
    opacity: 0;
  }

  .apexcharts-tooltip {
    border-radius: 5px;
    box-shadow: 2px 2px 6px -4px #999;
    cursor: default;
    font-size: 14px;
    left: 62px;
    opacity: 0;
    pointer-events: none;
    position: absolute;
    top: 20px;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    white-space: nowrap;
    z-index: 12;
    transition: 0.15s ease all;
  }

  .apexcharts-tooltip.apexcharts-active {
    opacity: 1;
    transition: 0.15s ease all;
  }

  .apexcharts-tooltip.apexcharts-theme-light {
    border: 1px solid #e3e3e3;
    background: rgba(255, 255, 255, 0.96);
  }

  .apexcharts-tooltip.apexcharts-theme-dark {
    color: #fff;
    background: rgba(30, 30, 30, 0.8);
  }

  .apexcharts-tooltip * {
    font-family: inherit;
  }


  .apexcharts-tooltip-title {
    padding: 6px;
    font-size: 15px;
    margin-bottom: 4px;
  }

  .apexcharts-tooltip.apexcharts-theme-light .apexcharts-tooltip-title {
    background: #ECEFF1;
    border-bottom: 1px solid #ddd;
  }

  .apexcharts-tooltip.apexcharts-theme-dark .apexcharts-tooltip-title {
    background: rgba(0, 0, 0, 0.7);
    border-bottom: 1px solid #333;
  }

  .apexcharts-tooltip-text-value,
  .apexcharts-tooltip-text-z-value {
    display: inline-block;
    font-weight: 600;
    margin-left: 5px;
  }

  .apexcharts-tooltip-text-z-label:empty,
  .apexcharts-tooltip-text-z-value:empty {
    display: none;
  }

  .apexcharts-tooltip-text-value,
  .apexcharts-tooltip-text-z-value {
    font-weight: 600;
  }

  .apexcharts-tooltip-marker {
    width: 12px;
    height: 12px;
    position: relative;
    top: 0px;
    margin-right: 10px;
    border-radius: 50%;
  }

  .apexcharts-tooltip-series-group {
    padding: 0 10px;
    display: none;
    text-align: left;
    justify-content: left;
    align-items: center;
  }

  .apexcharts-tooltip-series-group.apexcharts-active .apexcharts-tooltip-marker {
    opacity: 1;
  }

  .apexcharts-tooltip-series-group.apexcharts-active,
  .apexcharts-tooltip-series-group:last-child {
    padding-bottom: 4px;
  }

  .apexcharts-tooltip-series-group-hidden {
    opacity: 0;
    height: 0;
    line-height: 0;
    padding: 0 !important;
  }

  .apexcharts-tooltip-y-group {
    padding: 6px 0 5px;
  }

  .apexcharts-tooltip-candlestick {
    padding: 4px 8px;
  }

  .apexcharts-tooltip-candlestick>div {
    margin: 4px 0;
  }

  .apexcharts-tooltip-candlestick span.value {
    font-weight: bold;
  }

  .apexcharts-tooltip-rangebar {
    padding: 5px 8px;
  }

  .apexcharts-tooltip-rangebar .category {
    font-weight: 600;
    color: #777;
  }

  .apexcharts-tooltip-rangebar .series-name {
    font-weight: bold;
    display: block;
    margin-bottom: 5px;
  }

  .apexcharts-xaxistooltip {
    opacity: 0;
    padding: 9px 10px;
    pointer-events: none;
    color: #373d3f;
    font-size: 13px;
    text-align: center;
    border-radius: 2px;
    position: absolute;
    z-index: 10;
    background: #ECEFF1;
    border: 1px solid #90A4AE;
    transition: 0.15s ease all;
  }

  .apexcharts-xaxistooltip.apexcharts-theme-dark {
    background: rgba(0, 0, 0, 0.7);
    border: 1px solid rgba(0, 0, 0, 0.5);
    color: #fff;
  }

  .apexcharts-xaxistooltip:after,
  .apexcharts-xaxistooltip:before {
    left: 50%;
    border: solid transparent;
    content: " ";
    height: 0;
    width: 0;
    position: absolute;
    pointer-events: none;
  }

  .apexcharts-xaxistooltip:after {
    border-color: rgba(236, 239, 241, 0);
    border-width: 6px;
    margin-left: -6px;
  }

  .apexcharts-xaxistooltip:before {
    border-color: rgba(144, 164, 174, 0);
    border-width: 7px;
    margin-left: -7px;
  }

  .apexcharts-xaxistooltip-bottom:after,
  .apexcharts-xaxistooltip-bottom:before {
    bottom: 100%;
  }

  .apexcharts-xaxistooltip-top:after,
  .apexcharts-xaxistooltip-top:before {
    top: 100%;
  }

  .apexcharts-xaxistooltip-bottom:after {
    border-bottom-color: #ECEFF1;
  }

  .apexcharts-xaxistooltip-bottom:before {
    border-bottom-color: #90A4AE;
  }

  .apexcharts-xaxistooltip-bottom.apexcharts-theme-dark:after {
    border-bottom-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-xaxistooltip-bottom.apexcharts-theme-dark:before {
    border-bottom-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-xaxistooltip-top:after {
    border-top-color: #ECEFF1
  }

  .apexcharts-xaxistooltip-top:before {
    border-top-color: #90A4AE;
  }

  .apexcharts-xaxistooltip-top.apexcharts-theme-dark:after {
    border-top-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-xaxistooltip-top.apexcharts-theme-dark:before {
    border-top-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-xaxistooltip.apexcharts-active {
    opacity: 1;
    transition: 0.15s ease all;
  }

  .apexcharts-yaxistooltip {
    opacity: 0;
    padding: 4px 10px;
    pointer-events: none;
    color: #373d3f;
    font-size: 13px;
    text-align: center;
    border-radius: 2px;
    position: absolute;
    z-index: 10;
    background: #ECEFF1;
    border: 1px solid #90A4AE;
  }

  .apexcharts-yaxistooltip.apexcharts-theme-dark {
    background: rgba(0, 0, 0, 0.7);
    border: 1px solid rgba(0, 0, 0, 0.5);
    color: #fff;
  }

  .apexcharts-yaxistooltip:after,
  .apexcharts-yaxistooltip:before {
    top: 50%;
    border: solid transparent;
    content: " ";
    height: 0;
    width: 0;
    position: absolute;
    pointer-events: none;
  }

  .apexcharts-yaxistooltip:after {
    border-color: rgba(236, 239, 241, 0);
    border-width: 6px;
    margin-top: -6px;
  }

  .apexcharts-yaxistooltip:before {
    border-color: rgba(144, 164, 174, 0);
    border-width: 7px;
    margin-top: -7px;
  }

  .apexcharts-yaxistooltip-left:after,
  .apexcharts-yaxistooltip-left:before {
    left: 100%;
  }

  .apexcharts-yaxistooltip-right:after,
  .apexcharts-yaxistooltip-right:before {
    right: 100%;
  }

  .apexcharts-yaxistooltip-left:after {
    border-left-color: #ECEFF1;
  }

  .apexcharts-yaxistooltip-left:before {
    border-left-color: #90A4AE;
  }

  .apexcharts-yaxistooltip-left.apexcharts-theme-dark:after {
    border-left-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-yaxistooltip-left.apexcharts-theme-dark:before {
    border-left-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-yaxistooltip-right:after {
    border-right-color: #ECEFF1;
  }

  .apexcharts-yaxistooltip-right:before {
    border-right-color: #90A4AE;
  }

  .apexcharts-yaxistooltip-right.apexcharts-theme-dark:after {
    border-right-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-yaxistooltip-right.apexcharts-theme-dark:before {
    border-right-color: rgba(0, 0, 0, 0.5);
  }

  .apexcharts-yaxistooltip.apexcharts-active {
    opacity: 1;
  }

  .apexcharts-yaxistooltip-hidden {
    display: none;
  }

  .apexcharts-xcrosshairs,
  .apexcharts-ycrosshairs {
    pointer-events: none;
    opacity: 0;
    transition: 0.15s ease all;
  }

  .apexcharts-xcrosshairs.apexcharts-active,
  .apexcharts-ycrosshairs.apexcharts-active {
    opacity: 1;
    transition: 0.15s ease all;
  }

  .apexcharts-ycrosshairs-hidden {
    opacity: 0;
  }

  .apexcharts-selection-rect {
    cursor: move;
  }

  .svg_select_boundingRect, .svg_select_points_rot {
    pointer-events: none;
    opacity: 0;
    visibility: hidden;
  }
  .apexcharts-selection-rect + g .svg_select_boundingRect,
  .apexcharts-selection-rect + g .svg_select_points_rot {
    opacity: 0;
    visibility: hidden;
  }

  .apexcharts-selection-rect + g .svg_select_points_l,
  .apexcharts-selection-rect + g .svg_select_points_r {
    cursor: ew-resize;
    opacity: 1;
    visibility: visible;
  }

  .svg_select_points {
    fill: #efefef;
    stroke: #333;
    rx: 2;
  }

  .apexcharts-svg.apexcharts-zoomable.hovering-zoom {
    cursor: crosshair
  }

  .apexcharts-svg.apexcharts-zoomable.hovering-pan {
    cursor: move
  }

  .apexcharts-zoom-icon,
  .apexcharts-zoomin-icon,
  .apexcharts-zoomout-icon,
  .apexcharts-reset-icon,
  .apexcharts-pan-icon,
  .apexcharts-selection-icon,
  .apexcharts-menu-icon,
  .apexcharts-toolbar-custom-icon {
    cursor: pointer;
    width: 20px;
    height: 20px;
    line-height: 24px;
    color: #6E8192;
    text-align: center;
  }

  .apexcharts-zoom-icon svg,
  .apexcharts-zoomin-icon svg,
  .apexcharts-zoomout-icon svg,
  .apexcharts-reset-icon svg,
  .apexcharts-menu-icon svg {
    fill: #6E8192;
  }

  .apexcharts-selection-icon svg {
    fill: #444;
    transform: scale(0.76)
  }

  .apexcharts-theme-dark .apexcharts-zoom-icon svg,
  .apexcharts-theme-dark .apexcharts-zoomin-icon svg,
  .apexcharts-theme-dark .apexcharts-zoomout-icon svg,
  .apexcharts-theme-dark .apexcharts-reset-icon svg,
  .apexcharts-theme-dark .apexcharts-pan-icon svg,
  .apexcharts-theme-dark .apexcharts-selection-icon svg,
  .apexcharts-theme-dark .apexcharts-menu-icon svg,
  .apexcharts-theme-dark .apexcharts-toolbar-custom-icon svg {
    fill: #f3f4f5;
  }

  .apexcharts-canvas .apexcharts-zoom-icon.apexcharts-selected svg,
  .apexcharts-canvas .apexcharts-selection-icon.apexcharts-selected svg,
  .apexcharts-canvas .apexcharts-reset-zoom-icon.apexcharts-selected svg {
    fill: #008FFB;
  }

  .apexcharts-theme-light .apexcharts-selection-icon:not(.apexcharts-selected):hover svg,
  .apexcharts-theme-light .apexcharts-zoom-icon:not(.apexcharts-selected):hover svg,
  .apexcharts-theme-light .apexcharts-zoomin-icon:hover svg,
  .apexcharts-theme-light .apexcharts-zoomout-icon:hover svg,
  .apexcharts-theme-light .apexcharts-reset-icon:hover svg,
  .apexcharts-theme-light .apexcharts-menu-icon:hover svg {
    fill: #333;
  }

  .apexcharts-selection-icon,
  .apexcharts-menu-icon {
    position: relative;
  }

  .apexcharts-reset-icon {
    margin-left: 5px;
  }

  .apexcharts-zoom-icon,
  .apexcharts-reset-icon,
  .apexcharts-menu-icon {
    transform: scale(0.85);
  }

  .apexcharts-zoomin-icon,
  .apexcharts-zoomout-icon {
    transform: scale(0.7)
  }

  .apexcharts-zoomout-icon {
    margin-right: 3px;
  }

  .apexcharts-pan-icon {
    transform: scale(0.62);
    position: relative;
    left: 1px;
    top: 0px;
  }

  .apexcharts-pan-icon svg {
    fill: #fff;
    stroke: #6E8192;
    stroke-width: 2;
  }

  .apexcharts-pan-icon.apexcharts-selected svg {
    stroke: #008FFB;
  }

  .apexcharts-pan-icon:not(.apexcharts-selected):hover svg {
    stroke: #333;
  }

  .apexcharts-toolbar {
    position: absolute;
    z-index: 11;
    max-width: 176px;
    text-align: right;
    border-radius: 3px;
    padding: 0px 6px 2px 6px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .apexcharts-menu {
    background: #fff;
    position: absolute;
    top: 100%;
    border: 1px solid #ddd;
    border-radius: 3px;
    padding: 3px;
    right: 10px;
    opacity: 0;
    min-width: 110px;
    transition: 0.15s ease all;
    pointer-events: none;
  }

  .apexcharts-menu.apexcharts-menu-open {
    opacity: 1;
    pointer-events: all;
    transition: 0.15s ease all;
  }

  .apexcharts-menu-item {
    padding: 6px 7px;
    font-size: 12px;
    cursor: pointer;
  }

  .apexcharts-theme-light .apexcharts-menu-item:hover {
    background: #eee;
  }

  .apexcharts-theme-dark .apexcharts-menu {
    background: rgba(0, 0, 0, 0.7);
    color: #fff;
  }

  @media screen and (min-width: 768px) {
    .apexcharts-canvas:hover .apexcharts-toolbar {
      opacity: 1;
    }
  }

  .apexcharts-datalabel.apexcharts-element-hidden {
    opacity: 0;
  }

  .apexcharts-pie-label,
  .apexcharts-datalabels,
  .apexcharts-datalabel,
  .apexcharts-datalabel-label,
  .apexcharts-datalabel-value {
    cursor: default;
    pointer-events: none;
  }

  .apexcharts-pie-label-delay {
    opacity: 0;
    animation-name: opaque;
    animation-duration: 0.3s;
    animation-fill-mode: forwards;
    animation-timing-function: ease;
  }

  .apexcharts-canvas .apexcharts-element-hidden {
    opacity: 0;
  }

  .apexcharts-hide .apexcharts-series-points {
    opacity: 0;
  }

  .apexcharts-gridline,
  .apexcharts-annotation-rect,
  .apexcharts-tooltip .apexcharts-marker,
  .apexcharts-area-series .apexcharts-area,
  .apexcharts-line,
  .apexcharts-zoom-rect,
  .apexcharts-toolbar svg,
  .apexcharts-area-series .apexcharts-series-markers .apexcharts-marker.no-pointer-events,
  .apexcharts-line-series .apexcharts-series-markers .apexcharts-marker.no-pointer-events,
  .apexcharts-radar-series path,
  .apexcharts-radar-series polygon {
    pointer-events: none;
  }


  /* markers */

  .apexcharts-marker {
    transition: 0.15s ease all;
  }

  @keyframes opaque {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }


  /* Resize generated styles */

  @keyframes resizeanim {
    from {
      opacity: 0;
    }
    to {
      opacity: 0;
    }
  }

  .resize-triggers {
    animation: 1ms resizeanim;
    visibility: hidden;
    opacity: 0;
  }

  .resize-triggers,
  .resize-triggers>div,
  .contract-trigger:before {
    content: " ";
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    overflow: hidden;
  }

  .resize-triggers>div {
    background: #eee;
    overflow: auto;
  }

  .contract-trigger:before {
    width: 200%;
    height: 200%;
  }</style>
</head>
<body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed toolbar-enabled toolbar-fixed toolbar-tablet-and-mobile-fixed" style="--kt-toolbar-height:55px; --kt-toolbar-height-tablet-and-mobile:55px;" data-new-gr-c-s-check-loaded="8.876.0" data-gr-ext-installed="" data-kt-aside-minimize="on">
<div class="d-flex flex-column flex-root">
  <div class="page d-flex flex-row flex-column-fluid">

    <div class="wrapper d-flex flex-column flex-row-fluid" id="kt_wrapper">
      <div id="kt_header" style="" class="header align-items-stretch">
        <div class="container-fluid d-flex align-items-stretch justify-content-between">
          <div class="d-flex align-items-center d-lg-none ms-n3 me-1" title="Show aside menu">
            <div class="btn btn-icon btn-active-light-primary" id="kt_aside_mobile_toggle">
              <span class="svg-icon svg-icon-2x mt-1">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                      <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <rect x="0" y="0" width="24" height="24"></rect>
                        <rect fill="#000000" x="4" y="5" width="16" height="3" rx="1.5"></rect>
                        <path d="M5.5,15 L18.5,15 C19.3284271,15 20,15.6715729 20,16.5 C20,17.3284271 19.3284271,18 18.5,18 L5.5,18 C4.67157288,18 4,17.3284271 4,16.5 C4,15.6715729 4.67157288,15 5.5,15 Z M5.5,10 L18.5,10 C19.3284271,10 20,10.6715729 20,11.5 C20,12.3284271 19.3284271,13 18.5,13 L5.5,13 C4.67157288,13 4,12.3284271 4,11.5 C4,10.6715729 4.67157288,10 5.5,10 Z" fill="#000000" opacity="0.3"></path>
                      </g>
                    </svg>
                  </span>
            </div>
          </div>
          <div class="d-flex align-items-center flex-grow-1 flex-lg-grow-0">
            <a href="view/dashboard.jsp" class="d-lg-none">
              <img alt="Logo" src="/metronic8/demo1/assets/media/logos/logo-3.svg" class="h-30px">
            </a>
          </div>
          <div class="d-flex align-items-stretch justify-content-between flex-lg-grow-1">
            <div class="d-flex align-items-stretch" id="kt_header_nav"><div class="header-menu align-items-stretch" data-kt-drawer="true" data-kt-drawer-name="header-menu" data-kt-drawer-activate="{default: true, lg: false}" data-kt-drawer-overlay="true" data-kt-drawer-width="{default:'200px', '300px': '250px'}" data-kt-drawer-direction="end" data-kt-drawer-toggle="#kt_header_menu_mobile_toggle" data-kt-place="true" data-kt-place-mode="prepend" data-kt-place-parent="{default: '#kt_body', lg: '#kt_header_nav'}" style="">
              <div class="menu menu-lg-rounded menu-column menu-lg-row menu-state-bg menu-title-gray-700 menu-state-title-primary menu-state-icon-primary menu-state-bullet-primary menu-arrow-gray-400 fw-bold my-5 my-lg-0 align-items-stretch" id="#kt_header_menu" data-kt-menu="true">
                <div class="menu-item me-lg-1">
                  <a class="menu-link active py-3" href="view/dashboard.jsp">
                    <span class="menu-title">Dashboard</span>
                  </a>
                </div>
              </div>
            </div>
            </div>
          </div>
        </div>
      </div>
      <div class="content d-flex flex-column flex-column-fluid" id="kt_content">
        <div class="toolbar" id="kt_toolbar">
          <div id="kt_toolbar_container" class="container-fluid d-flex flex-stack"><div data-kt-place="true" data-kt-place-mode="prepend" data-kt-place-parent="{default: '#kt_content_container', 'lg': '#kt_toolbar_container'}" class="page-title d-flex align-items-center me-3 flex-wrap mb-5 mb-lg-0 lh-1">
            <h1 class="d-flex align-items-center text-dark fw-bolder my-1 fs-3">XRP Wallet Tools Dashboard WIP
              <span class="h-20px border-gray-200 border-start ms-3 mx-2"></span>
              <small class="text-muted fs-7 fw-bold my-1 ms-1">#1626454219972</small>
            </h1>
          </div>
          </div>
        </div>
        <div class="post d-flex flex-column-fluid" id="kt_post">
          <div id="kt_content_container" class="container">


            <p></p>
            <div class="row gy-5 gx-xl-8">
              <div class="col-xxl-12">
                <div class="card card-xxl-stretch mb-xl-3">
                  <div class="card-header border-0">
                    <h3 class="card-title fw-bolder text-dark">One-Click NFT Wallet Based Creator (Photo)</h3>
                    <div class="card-toolbar">
                    </div>
                  </div>
                  <div class="card-body pt-2">
                    <div class="alert alert-primary d-flex align-items-center p-5 mb-10">
                      <!--begin::Svg Icon | path: icons/duotone/General/Shield-check.svg-->
                      <span class="svg-icon svg-icon-2hx svg-icon-primary me-4">
						<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
							<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
								<rect x="0" y="0" width="24" height="24"></rect>
							    <path d="M4,4 L11.6314229,2.5691082 C11.8750185,2.52343403 12.1249815,2.52343403 12.3685771,2.5691082 L20,4 L20,13.2830094 C20,16.2173861 18.4883464,18.9447835 16,20.5 L12.5299989,22.6687507 C12.2057287,22.8714196 11.7942713,22.8714196 11.4700011,22.6687507 L8,20.5 C5.51165358,18.9447835 4,16.2173861 4,13.2830094 L4,4 Z" fill="#000000" opacity="0.3"></path>
							    <path d="M11.1750002,14.75 C10.9354169,14.75 10.6958335,14.6541667 10.5041669,14.4625 L8.58750019,12.5458333 C8.20416686,12.1625 8.20416686,11.5875 8.58750019,11.2041667 C8.97083352,10.8208333 9.59375019,10.8208333 9.92916686,11.2041667 L11.1750002,12.45 L14.3375002,9.2875 C14.7208335,8.90416667 15.2958335,8.90416667 15.6791669,9.2875 C16.0625002,9.67083333 16.0625002,10.2458333 15.6791669,10.6291667 L11.8458335,14.4625 C11.6541669,14.6541667 11.4145835,14.75 11.1750002,14.75 Z" fill="#000000"></path>
                            </g>
						</svg>
                      </span>
                      <!--end::Svg Icon-->
                      <div class="d-flex flex-column">
                        <h5 class="mb-1">INFO:</h5>
                        <span>Take note, You need a <b>TESTNET WALLET SEED KEY</b> for the Wallet Seed field.</span>
                      </div>
                    </div>
                    <form action="process.nftcreation" method="post" onsubmit="return checkSize(26214400)" novalidate="novalidate" enctype="multipart/form-data">
                      <div class="mb-10">
                        <label class="form-label">Wallet Seed:</label>
                        <input class="form-control form-control-solid" type="text" name="nftSeed" placeholder="" autocomplete="off">
                      </div>
                      <div class="mb-10">
                        <label class="form-label">NFT Name:</label>
                        <input class="form-control form-control-solid" type="text" name="nftName" placeholder="Amazing NFT!">
                      </div>
                      <div class="mb-10">
                        <label class="form-label">Author Name:</label>
                        <input class="form-control form-control-solid" type="text" name="nftAuthor" placeholder="Mr.NoOne">
                      </div>
                      <div class="mb-10">
                        <label class="form-label">Author Email:</label>
                        <input class="form-control form-control-solid" placeholder="" type="text" name="nftEmail">
                      </div><div class="mb-10">
                      <label class="form-label">Your Twitter:</label>
                      <input class="form-control form-control-solid" placeholder="" type="text" name="nftTwitter">
                    </div><div class="mb-10">
                      <label class="form-label">NFT Description:</label>
                      <input placeholder="" type="text" name="nftDescription" class="form-control form-control-solid">
                    </div>
                      <!--begin::Input group-->
                      <input type="file" name="file" id="upload" class="btn btn-outline btn-outline-dashed btn-outline-primary btn-active-light-primary w-100">
                      <br>
                      <!--end::Input group-->
                      <br>
                      <button type="submit" class="btn btn-lg btn-primary w-100 mb-5">Create NFT</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
            <div class="row gy-5 gx-xl-8">
              <div class="col-xxl-12">

              </div>
            </div>
          </div>
        </div>
        <div class="footer py-4 d-flex flex-lg-column" id="kt_footer">
          <div class="container-fluid d-flex flex-column flex-md-row align-items-center justify-content-between">
            <div class="text-dark order-2 order-md-1">
              <span class="text-muted fw-bold me-1">2021</span>
              <a href="view/dashboard.jsp" target="_blank" class="text-gray-800 text-hover-primary">XRP Wallet Tools</a>
            </div>
            <ul class="menu menu-gray-600 menu-hover-primary fw-bold order-1">
              <li class="menu-item">
                <a href="" target="_blank" class="menu-link px-2">About</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div id="kt_scrolltop" class="scrolltop" data-kt-scrolltop="true">
    <span class="svg-icon">
        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
          <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
            <polygon points="0 0 24 0 24 24 0 24"></polygon>
            <rect fill="#000000" opacity="0.5" x="11" y="10" width="2" height="10" rx="1"></rect>
            <path d="M6.70710678,12.7071068 C6.31658249,13.0976311 5.68341751,13.0976311 5.29289322,12.7071068 C4.90236893,12.3165825 4.90236893,11.6834175 5.29289322,11.2928932 L11.2928932,5.29289322 C11.6714722,4.91431428 12.2810586,4.90106866 12.6757246,5.26284586 L18.6757246,10.7628459 C19.0828436,11.1360383 19.1103465,11.7686056 18.7371541,12.1757246 C18.3639617,12.5828436 17.7313944,12.6103465 17.3242754,12.2371541 L12.0300757,7.38413782 L6.70710678,12.7071068 Z" fill="#000000" fill-rule="nonzero"></path>
          </g>
        </svg>
      </span>
  </div>
  <script src="assets/plugins/global/plugins.bundle.js"></script>
  <script src="assets/js/scripts.bundle.js"></script>
  <script src="assets/js/custom/widgets.js"></script>
  <script src="assets/js/custom/intro.js"></script>
  <script src="assets/js/custom/documentation/forms/dropzonejs.js"></script>
  <script type="text/javascript">
    function checkSize(max_img_size)
    {
      var input = document.getElementById("upload");
      if(input.files && input.files.length == 1)
      {
        if (input.files[0].size > max_img_size)
        {
          alert("The file must be less than " + (max_img_size/1024/1024) + "MB");
          return false;
        }
      }

      return true;
    }
  </script>
</div>
</body></html>