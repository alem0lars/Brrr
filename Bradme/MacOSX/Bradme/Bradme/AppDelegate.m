//
//  AppDelegate.m
//  Bradme
//
//  Created by Alessandro Molari on 2013/11/20.
//  Copyright (c) 2013 Nextreamlabs. All rights reserved.
//

#import "MasterViewController.h"
#import "AppDelegate.h"


@interface  AppDelegate()

@property (nonatomic,strong) IBOutlet MasterViewController *masterViewController;

@end


@implementation AppDelegate

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
    // 1. Create the master View Controller
    self.masterViewController = [[MasterViewController alloc] initWithNibName:@"MasterViewController" bundle:nil];
    
    // 2. Add the view controller to the Window's content view
    [self.window.contentView addSubview:self.masterViewController.view];
    self.masterViewController.view.frame = ((NSView*)self.window.contentView).bounds;
}

@end
