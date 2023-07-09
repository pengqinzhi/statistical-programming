function varargout = helloworld(varargin)
% HELLOWORLD MATLAB code for helloworld.fig
%      HELLOWORLD, by itself, creates a new HELLOWORLD or raises the existing
%      singleton*.
%
%      H = HELLOWORLD returns the handle to a new HELLOWORLD or the handle to
%      the existing singleton*.
%
%      HELLOWORLD('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in HELLOWORLD.M with the given input arguments.
%
%      HELLOWORLD('Property','Value',...) creates a new HELLOWORLD or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before helloworld_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to helloworld_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help helloworld

% Last Modified by GUIDE v2.5 11-May-2020 14:24:36

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @helloworld_OpeningFcn, ...
                   'gui_OutputFcn',  @helloworld_OutputFcn, ...
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


% --- Executes just before helloworld is made visible.
function helloworld_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to helloworld (see VARARGIN)

% Choose default command line output for helloworld
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes helloworld wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = helloworld_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in helloBt.
function helloBt_Callback(hObject, eventdata, handles) 
set(handles.helloStr,'String','Hello World !');
% hObject    handle to helloBt (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
