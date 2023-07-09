function varargout = xinhao(varargin)
% XINHAO MATLAB code for xinhao.fig
%      XINHAO, by itself, creates a new XINHAO or raises the existing
%      singleton*.
%
%      H = XINHAO returns the handle to a new XINHAO or the handle to
%      the existing singleton*.
%
%      XINHAO('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in XINHAO.M with the given input arguments.
%
%      XINHAO('Property','Value',...) creates a new XINHAO or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before xinhao_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to xinhao_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help xinhao

% Last Modified by GUIDE v2.5 11-May-2020 15:34:30

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @xinhao_OpeningFcn, ...
                   'gui_OutputFcn',  @xinhao_OutputFcn, ...
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


% --- Executes just before xinhao is made visible.
function xinhao_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to xinhao (see VARARGIN)

% Choose default command line output for xinhao
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes xinhao wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = xinhao_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;



function f1_input_Callback(hObject, eventdata, handles)

% hObject    handle to f1_input (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of f1_input as text
%        str2double(get(hObject,'String')) returns contents of f1_input as a double


% --- Executes during object creation, after setting all properties.
function f1_input_CreateFcn(hObject, eventdata, handles)
% hObject    handle to f1_input (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function f2_input_Callback(hObject, eventdata, handles)
% hObject    handle to f2_input (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of f2_input as text
%        str2double(get(hObject,'String')) returns contents of f2_input as a double


% --- Executes during object creation, after setting all properties.
function f2_input_CreateFcn(hObject, eventdata, handles)
% hObject    handle to f2_input (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function t_input_Callback(hObject, eventdata, handles)
% hObject    handle to t_input (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of t_input as text
%        str2double(get(hObject,'String')) returns contents of t_input as a double


% --- Executes during object creation, after setting all properties.
function t_input_CreateFcn(hObject, eventdata, handles)
% hObject    handle to t_input (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in pushbutton1.
function pushbutton1_Callback(hObject, eventdata, handles)
f1=str2double(get(handles.f1_input,'String'));
f2=str2double(get(handles.f2_input,'String'));
t=eval(get(handles.t_input,'String'));

fs=1/(t(2)-t(1)); %采样频率
x=sin(2*pi*f1*t)+sin(2*pi*f2*t);
y=fft(x,512); %512个数据点进行快速傅里叶变换 
m=abs(y); %取频域的幅值
f=fs*(0:256)/512; %取频域的分辨率

axes(handles.frequency_axes) %选取句柄所示坐标轴绘图 
plot(f,m(1:257)) 
set(handles.frequency_axes,'XminorTick','on')
grid on

axes(handles.time_axes)
plot(t,x) 
set(handles.time_axes,'XminorTick','on') 
grid on
% hObject    handle to pushbutton1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
