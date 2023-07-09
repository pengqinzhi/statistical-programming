function varargout = GUIPlot(varargin)
% GUIPLOT MATLAB code for GUIPlot.fig
%      GUIPLOT, by itself, creates a new GUIPLOT or raises the existing
%      singleton*.
%
%      H = GUIPLOT returns the handle to a new GUIPLOT or the handle to
%      the existing singleton*.
%
%      GUIPLOT('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in GUIPLOT.M with the given input arguments.
%
%      GUIPLOT('Property','Value',...) creates a new GUIPLOT or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before GUIPlot_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to GUIPlot_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help GUIPlot

% Last Modified by GUIDE v2.5 11-May-2020 16:01:27

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @GUIPlot_OpeningFcn, ...
                   'gui_OutputFcn',  @GUIPlot_OutputFcn, ...
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


% --- Executes just before GUIPlot is made visible.
function GUIPlot_OpeningFcn(hObject, eventdata, handles, varargin)
......
......
handles.peaks=peaks(35);
handles.membrane=membrane;
% Set the current data value.
handles.current_data = handles.peaks;
contour(handles.current_data)
% Update handles structure
guidata(hObject, handles);
%该函数首先设置初始数据为 peaks 数据，且初始图形为等值线。

% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to GUIPlot (see VARARGIN)

% Choose default command line output for GUIPlot
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes GUIPlot wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = GUIPlot_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on selection change in popupmenu1.
function popupmenu1_Callback(hObject, eventdata, handles)
str = get(hObject, 'String');
val = get(hObject,'Value');
% Set current data to the selected data set.
switch str{val}
case 'Peaks' % User selects peaks 
    handles.current_data = handles.peaks;
case 'Membrane' % User selects membrane 
    handles.current_data = handles.membrane;
end
% Save the handles structure.
guidata(hObject,handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns popupmenu1 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu1


% --- Executes during object creation, after setting all properties.
function popupmenu1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in surf_pushbutton.
function surf_pushbutton_Callback(hObject, eventdata, handles)
surf(handles.current_data);
% hObject    handle to surf_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in mesh_pushbutton.
function mesh_pushbutton_Callback(hObject, eventdata, handles)
mesh(handles.current_data);
% hObject    handle to mesh_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in contour_pushbutton.
function contour_pushbutton_Callback(hObject, eventdata, handles)
contour(handles.current_data);
% hObject    handle to contour_pushbutton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
