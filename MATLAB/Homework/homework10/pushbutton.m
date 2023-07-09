function varargout = pushbutton(varargin)
% PUSHBUTTON MATLAB code for pushbutton.fig
%      PUSHBUTTON, by itself, creates a new PUSHBUTTON or raises the existing
%      singleton*.
%
%      H = PUSHBUTTON returns the handle to a new PUSHBUTTON or the handle to
%      the existing singleton*.
%
%      PUSHBUTTON('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in PUSHBUTTON.M with the given input arguments.
%
%      PUSHBUTTON('Property','Value',...) creates a new PUSHBUTTON or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before pushbutton_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to pushbutton_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help pushbutton

% Last Modified by GUIDE v2.5 11-May-2020 14:38:32

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @pushbutton_OpeningFcn, ...
                   'gui_OutputFcn',  @pushbutton_OutputFcn, ...
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


% --- Executes just before pushbutton is made visible.
function pushbutton_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to pushbutton (see VARARGIN)

% Choose default command line output for pushbutton
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes pushbutton wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = pushbutton_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in helloBt.
function helloBt_Callback(hObject, eventdata, handles)
persistent c
if isempty(c)
    c=0;
end
c=c+1;
s=strcat('Total Clicks:',num2str(c));
set(handles.helloStr,'String',s);
% hObject    handle to helloBt (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
