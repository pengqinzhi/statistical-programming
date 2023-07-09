
function varargout = work1_17041702(varargin)
% WORK1_17041702 MATLAB code for work1_17041702.fig
%      WORK1_17041702, by itself, creates a new WORK1_17041702 or raises the existing
%      singleton*.
%
%      H = WORK1_17041702 returns the handle to a new WORK1_17041702 or the handle to
%      the existing singleton*.
%
%      WORK1_17041702('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in WORK1_17041702.M with the given input arguments.
%
%      WORK1_17041702('Property','Value',...) creates a new WORK1_17041702 or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before work1_17041702_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to work1_17041702_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help work1_17041702

% Last Modified by GUIDE v2.5 01-Jun-2020 14:10:08

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @work1_17041702_OpeningFcn, ...
                   'gui_OutputFcn',  @work1_17041702_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before work1_17041702 is made visible.
function work1_17041702_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to work1_17041702 (see VARARGIN)

% Choose default command line output for work1_17041702
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes work1_17041702 wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = work1_17041702_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in guanbi.
function guanbi_Callback(hObject, eventdata, handles)
close
% hObject    handle to guanbi (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in jiahao.
function jiahao_Callback(hObject, eventdata, handles)
x = 0:0.2*pi/10:2*pi; y = sin(x);   %绘图
plot(x,y,'+')
title('标记切换 ')
ylabel('因变量y');
xlabel('自变量x');

% hObject    handle to jiahao (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in yuanquan.
function yuanquan_Callback(hObject, eventdata, handles)
x = 0:0.2*pi/10:2*pi; y = sin(x);   %绘图
plot(x,y,'o')
title('标记切换 ')
ylabel('因变量y');
xlabel('自变量x');
% hObject    handle to yuanquan (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in chahao.
function chahao_Callback(hObject, eventdata, handles)
x = 0:0.2*pi/10:2*pi; y = sin(x);   %绘图
plot(x,y,'x')
title('标记切换 ')
ylabel('因变量y');
xlabel('自变量x');
% hObject    handle to chahao (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes during object creation, after setting all properties.
function axes1_CreateFcn(hObject, eventdata, handles)
x = 0:0.2*pi/10:2*pi; y = sin(x);   %绘图
plot(x,y,'*')
title('标记切换 ')
ylabel('因变量y');
xlabel('自变量x');

% hObject    handle to axes1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: place code in OpeningFcn to populate axes1
