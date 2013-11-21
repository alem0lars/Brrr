//
//  MasterViewController.m
//  Bradme
//
//  Created by Alessandro Molari on 2013/11/20.
//  Copyright (c) 2013 Nextreamlabs. All rights reserved.
//

#import "MasterViewController.h"

@interface MasterViewController ()

@property (weak) IBOutlet NSButton *btnToggleBrol;
@property (weak) IBOutlet NSButton *btnToggleReader;
@property (weak) IBOutlet NSButton *btnToggleBufferer;
@property (weak) IBOutlet NSButton *btnTogglePusher;

@property (weak) IBOutlet NSTextField *lblBrolStatus;
@property (weak) IBOutlet NSTextField *lblReaderStatus;
@property (weak) IBOutlet NSTextField *lblBuffererStatus;
@property (weak) IBOutlet NSTextField *lblPusherStatus;

@property (strong, nonatomic) NSMutableDictionary *statuses;

typedef enum BrrrComponentType {
    Brol, Reader, Bufferer, Pusher
} BrrrComponentType;

typedef enum BrrrComponentStatusType {
    Starting, Started, Stopped
} BrrrComponentStatusType;

@end

@implementation MasterViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        
        self.statuses = [[NSMutableDictionary alloc] initWithCapacity:0];
        
        [self.statuses setObject:BrrrComponentStatusType.Stopped forKey:BrrrComponentType.Brol];
        [self.statuses setObject:BrrrComponentStatusType.Stopped
                          forKey:BrrrComponentType.Reader];
        [self.statuses setObject:BrrrComponentStatusType.Stopped
                          forKey:BrrrComponentType.Bufferer];
        [self.statuses setObject:BrrrComponentStatusType.Stopped
                          forKey:BrrrComponentType.Pusher];
    }
    
    return self;
}

-(void) setStatusForComponent:(BrrrComponentType)type enabled:(BOOL)isEnabled
{
    // TODO
    
    [self setStatusBtnForComponent:type enabled:isEnabled andTitle:<#(NSString *)#>]
}

-(void) setStatusBtnForComponent:(BrrrComponentType)type enabled:(BOOL)isEnabled andTitle:(NSString *) title
{
    switch (type) {
        case Brol:
            [self.btnToggleBrol setEnabled:isEnabled];
            [self.btnToggleBrol setTitle:title];
            break;
        case Reader:
            [self.btnToggleReader setEnabled:isEnabled];
            [self.btnToggleReader setTitle:title];
            break;
        case Bufferer:
            [self.btnToggleBufferer setEnabled:isEnabled];
            [self.btnToggleBufferer setTitle:title];
            break;
        case Pusher:
            [self.btnTogglePusher setEnabled:isEnabled];
            [self.btnTogglePusher setTitle:title];
            break;
        default:
            break;
    }
}

@end
