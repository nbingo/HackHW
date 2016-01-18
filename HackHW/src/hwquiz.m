
function varargout = hwquiz(varargin)
% HWQUIZ MATLAB code for hwquiz.fig
%      HWQUIZ, by itself, creates a new HWQUIZ or raises the existing
%      singleton*.
%
%      H = HWQUIZ returns the handle to a new HWQUIZ or the handle to
%      the existing singleton*.
%
%      HWQUIZ('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in HWQUIZ.M with the given input arguments.
%
%      HWQUIZ('Property','Value',...) creates a new HWQUIZ or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before hwquiz_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to hwquiz_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help hwquiz

% Last Modified by GUIDE v2.5 17-Jan-2016 13:43:56

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @hwquiz_OpeningFcn, ...
                   'gui_OutputFcn',  @hwquiz_OutputFcn, ...
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


% --- Executes just before hwquiz is made visible.
function hwquiz_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to hwquiz (see VARARGIN)

% Choose default command line output for hwquiz
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

axes(handles.axes1);
global currentImage;
currentImage = imread('gray.png');

image(currentImage);
axis off
axis image
% UIWAIT makes hwquiz wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = hwquiz_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in pushbutton1.
function pushbutton1_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

[fname,thepath] = uigetfile('*.txt');
handles.notes.String = fname;

fullpath = [thepath fname];
outpath = [thepath 'qa_' fname];

import personalNotes;
personalNotes.setPath(fullpath);
personalNotes.setOutPath(outpath);
personalNotes.main('');

% Nomi - here call the java method with the full path:
% generate_questions_answers(fullpath)
% and have the result left in the same directory with
% the file name questions.txt

% read the questions/answers...

 fid = fopen(outpath);  %% you have to replacet his when ready....

%fid = fopen('out.txt');

global Q;

Q = {};
q = fgetl(fid);
while(ischar(q))
    Q{end+1} = {q fgetl(fid)};
    q = fgetl(fid);
end
fclose(fid);

% --- Executes on button press in quiz.
function quiz_Callback(hObject, eventdata, handles)
% hObject    handle to quiz (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

global Q qn correct;
correct  = 0;
% J = randperm(length(Q)); % order the questions randomly
Q = Q(randperm(length(Q)));
qn = 1;
handles.question.String = Q{qn}{1};



function answer_Callback(hObject, eventdata, handles)
% hObject    handle to answer (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of answer as text
%        str2double(get(hObject,'String')) returns contents of answer as a double

global Q J qn correct;

ans = hObject.String;

if(personalNotes.semanticCompare(ans,Q{qn}{2}))  %% more intelligent comparison here...
    correct = correct + 1;
    global  currentImage;
    currentImage = imread('check.png');
    axes(handles.axes1);
    image(currentImage);
    axis off;
    axis image;
    handles.status.String = sprintf('Correct! (%d/%d)',correct,qn);
elseif(strcmp(Q{qn}{2},'~'))
    handles.status.String = sprintf('Sorry, not able to parse answer.');
else
    handles.status.String = sprintf('Incorrect! (%d/%d)\nThe correct answer was: %s',correct,qn,Q{qn}{2});
    axes(handles.axes1);
    image(imread('wrong.png'));
    axis off;
    axis image;
end
handles.answer.String = '';
axes(handles.axes1);
image(imread('gray.png'));
axis off;
axis image;

if(qn==length(Q))
    handles.status.String = sprintf('Test Complete! Score of %d out of %d correct.',correct,qn);
else
    qn = qn + 1;
    handles.question.String = Q{qn}{1};
end


% --- Executes during object creation, after setting all properties.
function answer_CreateFcn(hObject, eventdata, handles)
% hObject    handle to answer (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

% --- Executes on button press in pushbutton4.
function pushbutton4_Callback(hObject, eventdata, handles)
    close
% hObject    handle to pushbutton4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes during object creation, after setting all properties.
function axes1_CreateFcn(hObject, eventdata, handles)
    
% hObject    handle to axes1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: place code in OpeningFcn to populate axes1


% --- Executes when figure1 is resized.
function figure1_SizeChangedFcn(hObject, eventdata, handles)
% hObject    handle to figure1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
