x = -3*pi:pi/10:3*pi;
y1 = sin(x);
y2 = cos(x);
h1 = plot(x,y1);
set(h1,'LineWidth',2);
hold on
h2 = plot(x,y2);
set(h2,'LineWidth',2,'LineStyle',':','Color','r');
title('\bfplot of sin \itx \rm\bf and cos \itx');
xlabel('\bf\itx')
ylabel(']bfsin \itx \rm\bf and cos \itx');
legend ('sine','cosine');
hold off

k = waitforbuttonpress;
while k == 0
    handle = gco;
    type = get(handle,'Type');
    disp(['Object type = ' type '.']);
    yn = input('Do you want to display details?(y/n)','s');
    if yn == 'y' 
        details = get(handle);
        disp(details);
    end
    k = waitforbuttonpress;
end

    